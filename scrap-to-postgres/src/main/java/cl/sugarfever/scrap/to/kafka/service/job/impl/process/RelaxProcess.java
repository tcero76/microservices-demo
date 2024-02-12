package cl.sugarfever.scrap.to.kafka.service.job.impl.process;


import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.scrap.to.kafka.service.listener.JobListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Times;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RelaxProcess implements ItemProcessor<Document, Ts> {

    private final JobListener jobExecutionListener;


    @Override
    public Ts process(Document document) throws Exception {
        Set<Servicio> servicios = getServicios(document);
        Set<Imagen> imagenes = getImagenes(document);
        String idpagina = document.select("div.header-ficha>a.add-to-favorites").attr("data-item-id");
        Ts ts = Ts.builder()
                .id_ts(UUID.randomUUID())
                .nombre(getNombre(document))
                .idpagina(getIdpagina(document))
                .edad(getEdad(document))
                .sexo("")
                .seccion("")
                .video("")
                .descripcion(document.select("div.desc-ficha").text())
                .idjob(jobExecutionListener.getIdJob())
                .ciudad("Santiago")
                .cartel("RelaxChile")
                .sitio("www.relaxchile.cl")
                .servicios(servicios)
                .imagenes(imagenes)
                .valor(getValor(document))
                .medidas(getMedidas(document))
                .estatura(getEstatura(document))
                .imagen(getImagen(imagenes))
                .telefono(getTelefono(document))
                .atencion(getAtencion(document))
                .ubicacion(getUbicacion(document))
                .depilacion("")
                .horario("")
                .fecharegistro(Timestamp.from(Instant.now()).getTime())
                .build();
        for(Servicio s:servicios) {
            s.setTs(ts);
        }
        for(Imagen i:imagenes) {
            i.setTs(ts);
        }
        log.info("TS: Se procesó correctamente ts: {}", ts.getNombre());
        return ts;
    }

    private String getUbicacion(Document document) {
        return document.select("div.atencion-ficha>div").get(1).text();
    }

    private String getAtencion(Document document) {
        return document.select("div.atencion-ficha>div").get(1).text();
    }

    private String getTelefono(Document document) {
        return document.select("span.ficha-fono").get(0).text();
    }

    private String getImagen(Set<Imagen> imagenes) {
        if(imagenes.size()>0) {
            return imagenes.iterator().next().getUrl();
        }
        return null;
    }

    private String getEstatura(Document document) {
        return document.select("table.tabla-medidas>tbody>tr>td").get(1).text().replaceAll("Altura: ", "");

    }

    private int getIdpagina(Document document) {
        String idpagina = document.select("div.header-ficha>a.add-to-favorites").attr("data-item-id");
        Integer resp = 0;
         try {
             resp = Integer.valueOf(idpagina);
         } catch (Exception e) {
             log.error("Edad: Error al leer idPagina: {}", idpagina);
         }
         return resp;
    }

    private String getVideo(Document document, String idpagina) {
        Set<String> videos = new HashSet<>();
        Elements videosTag = document.select("div.info-ficha-video>div");
        Integer i = 0;
        for(Element e:videosTag) {
            i++;
            videos.add("https://www.relaxchile.cl/video?escort=" + idpagina + "&cual=" + i);
        }
        if(videos.size()>0){
            return videos.iterator().next();
        }
        return null;
    }

    private String getMedidas(Document document) {
        return document.select("table.tabla-medidas>tbody>tr>td").get(3).text().replaceAll("Medidas: ", "");
    }

    private String getValor(Document document) {
        Elements valor = document.select("div.valor-ficha");
        if(valor.isEmpty()) {
            return "";
        }
        return valor.text();
    }

    private Set<Imagen> getImagenes(Document document) {
        Set<Imagen> imagenes = new HashSet<>();
        Elements elements = document.select("div#galeria>div.cont-galeria");
        for(Element e:elements) {
            imagenes.add(Imagen.builder()
                            .id_imagen(UUID.randomUUID())
                            .url(e.select("div.chicaFoto>img").attr("src"))
                    .build());
        }
        return imagenes;
    }

    private Set<Servicio> getServicios(Document document) {
        String text = document.select("div.servicios-ficha").text();
        Set<Servicio> servicio = new HashSet<>();
        text = text.replace("Servicios:","");
        for(String s:text.split(",")) {
            servicio.add(Servicio.builder()
                .id_servicio(UUID.randomUUID())
                .adicional(false)
                .nombre(s)
                .build());
        }
        return servicio;
    }

    private int getEdad(Document document) {
        String strEdad = document.select("table.tabla-medidas>tbody>tr>td").get(0).text().replaceAll("[a-z A-Z Ñ ñ : ]", "");
        if (strEdad.isEmpty()) {
            return 0;
        }
        Integer edad = 0;
        try {
            edad = Integer.valueOf(strEdad);
        } catch (NumberFormatException e) {
            log.error("EDAD: No se pudo leer la Edad en: {} ts: {}", strEdad, getNombre(document));
            log.error(e.getMessage());
        }
        return edad;

    }

    private static String getNombre(Document document) {
        return document.select("div.info-ficha>p#nombre-escort").text();
    }
}
