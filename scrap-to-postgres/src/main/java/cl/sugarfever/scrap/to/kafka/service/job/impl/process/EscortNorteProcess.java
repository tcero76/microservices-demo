package cl.sugarfever.scrap.to.kafka.service.job.impl.process;

import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.scrap.to.kafka.service.listener.JobListener;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EscortNorteProcess implements ItemProcessor<Document, Ts> {

    @Autowired
    private JobListener jobExecutionListener;

    @Override
    public Ts process(Document doc) throws Exception {
        Elements cuadroArriba = doc.select("dl#datos1 dd");
        String precio = "";
        if (doc.select("dl#promo p").size()>0) {
            if (doc.select("dl#promo p").get(0).childNodes().size()>0) {
                String valor = doc.select("dl#promo p").get(0).childNodes().get(0).toString();
                precio = valor.replaceAll("[^0-9]","");
            }
        }

        List<Node> node = doc.select("ul#serinc").get(0).childNodes();
        Set<Servicio> servicios = new HashSet<Servicio>();
        for (int i = 3; i < node.size(); i+=2) {
            Servicio s = Servicio.builder()
                    .id_servicio(UUID.randomUUID())
                    .nombre(node.get(i).childNodes().toString())
                    .adicional(false)
                    .build();
            servicios.add(s);
        }
        node = doc.select("ul#servad").get(0).childNodes();
        for (int i = 3; i < node.size(); i+=2) {
            Servicio s = Servicio.builder()
                    .id_servicio(UUID.randomUUID())
                    .nombre(node.get(i).childNodes().toString())
                    .adicional(true)
                    .build();
            servicios.add(s);
        }
        Integer idpagina = Integer.valueOf(doc.baseUri().substring(47));
        Set<Imagen> imagenes = parsearImagenes(idpagina);
        Ts ts = Ts.builder()
                .id_ts(UUID.randomUUID())
                .idpagina(idpagina)
                .nombre(doc.select("h1#nombre").text())
                .sexo("")
                .edad(Integer.valueOf(cuadroArriba.get(1).text().replaceAll("[^0-9]", "")))
                .medidas(cuadroArriba.get(2).text())
                .seccion("")
                .depilacion(cuadroArriba.get(0).text())
                .ubicacion(doc.select("dl#sector dd").text())
                .horario(doc.select("div#datos5 dd").text())
                .descripcion(doc.select("div#texto").text())
                .atencion(doc.select("dl#datos3 dd").text())
                .telefono(doc.select("dl#contacto dt").text().replaceAll("[^0-9]", ""))
                .estatura(doc.select("dl#datos2 dd").get(0).text())
                .valor(precio)
                .servicios(servicios)
                .cartel("escortnorte")
                .sitio(doc.location())
                .idjob(jobExecutionListener.getIdJob())
                .ciudad(doc.select("h2#fichaseccion").text())
                .imagenes(imagenes.size()>0?imagenes:(new HashSet<Imagen>()))
                .imagen(imagenes.stream().findFirst().isEmpty()?null:imagenes.stream().findFirst().get().getUrl())
                .video("")
                .build();
        log.info("Ts: capturado: {}", ts.getNombre());
        if (imagenes != null) {
            imagenes.forEach(imagen -> imagen.setTs(ts));
        }
        if (servicios != null) {
            servicios.forEach(servicio -> servicio.setTs(ts));
        }
        return ts;
    }


    private static Set<Imagen> parsearImagenes(Integer idpagina) {
        Response res = null;
        try {
            res = Jsoup
                    .connect("https://escortnorte.cl/_js/ajax/cargar_galeria_ficha.php?fichaID=" + idpagina)
                    .header("User-Agent",
                            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:66.0) Gecko/20100101 Firefox/66.0")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .method(Method.GET)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DocumentBuilder dBuilder;
        Set<Imagen> imagenes = new HashSet<Imagen>();
        try {
            dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            org.w3c.dom.Document xml = dBuilder.parse(new StringBufferInputStream(res.body()));
            NodeList nodes = xml.getChildNodes();
            for (int j = 1; j < nodes.item(0).getChildNodes().getLength(); j+=2) {
                Imagen img = Imagen.builder()
                        .id_imagen(UUID.randomUUID())
                        .url(nodes.item(0).getChildNodes().item(j).getChildNodes().item(1).getTextContent().replace("./..", "https://escortnorte.cl"))
                        .build();
                imagenes.add(img);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagenes;
    }

}
