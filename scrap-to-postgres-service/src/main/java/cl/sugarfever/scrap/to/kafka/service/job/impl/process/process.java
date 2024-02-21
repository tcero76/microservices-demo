package cl.sugarfever.scrap.to.kafka.service.job.impl.process;

import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.scrap.to.kafka.service.listener.JobListener;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Component
@Qualifier("StandardProcess")
@Slf4j
public class process implements ItemProcessor<Document, Ts> {
    @Autowired
    private JobListener jobExecutionListener;

    @Override
    public Ts process(Document doc) throws Exception {
        Set<Imagen> imagenes = getImagenes(doc);
        Set<Servicio> servicios = getServicios(doc);
        Ts ts = Ts.builder()
                .id_ts(UUID.randomUUID())
                .nombre(getNombre(doc,"div.informacion-b1>div.nombre-perfil"))
                .edad(getEdad(doc))
                .medidas(getMedidas(doc, "li[aria-label=Medidas:]"))
                .idpagina(getIdpagina(doc))
                .depilacion(getDepilacion(doc))
                .ubicacion(getUbicacion(doc))
                .descripcion(getText(doc))
                .horario(getHorario(doc))
                .telefono(getTelefono(doc))
                .estatura(getEstatura(doc))
                .valor(getValor(doc))
                .idjob(jobExecutionListener.getIdJob())
                .fecharegistro(new Date().getTime())
                .sitio(doc.location())
                .cartel(getCartel(doc))
                .imagenes(imagenes)
                .imagen(getImagen(imagenes, doc))
                .servicios(getServicios(doc))
                .video(getVideo(doc))
                .ciudad(getCiudad(doc))
                .atencion(getAtencion(doc))
                .seccion("")
                .sexo("")
                .video("")
                .fecharegistro(Timestamp.from(Instant.now()).getTime())
                .build();
        if (imagenes != null) {
            imagenes.forEach(imagen -> imagen.setTs(ts));
        }
        if (servicios != null) {
            servicios.forEach(servicio -> servicio.setTs(ts));
        }
        log.info("Ts: capturado: {}", ts.getNombre());
        return ts;
    }

    private String getAtencion(Document doc) {
        return doc.select("div.horario_info>div.text-1>p").text();
    }

    private String getCiudad(Document doc) {
        String locality = doc.select("meta[name=locality]").attr("content");
        if ( locality.contains("Viña")) {
            return "Viña del Mar";
        } else if (locality.contains("Santiago")) {
            return "Santiago";
        }
        log.error("CIUDAD: No se encontró ciudad en: {}", locality);
        return null;
    }

    private String getVideo(Document doc) {
        Set<String> video = new HashSet<>();
        Elements elements = doc.select("div.videos-seccion-grid>div.video-content");
        if(elements.size()>0) {
            for(Element element:elements) {
                video.add(element.select("source").attr("src"));
            }
            return video.iterator().next();
        }
        return null;
    }

    private String getImagen(Set<Imagen> imagenes, Document doc) {
        if (imagenes.size()>0) {
            return imagenes.iterator().next().getUrl();
        } else {
            log.error("IMAGEN: No se encontraron imágenes en: {}", getNombre(doc, "div.informacion-b1>div.nombre-perfil"));
            return null;
        }
    }
    private Set<Servicio> getServicios(Document doc) {
        Set<Servicio> servicios = new HashSet<>();
        Elements elements = doc.select("div.servicios-modal>ul.incluidos-modal>li");
        for(Element e:elements) {
            if(!e.text().contains("Incluidos")) {
                Servicio servicio = new Servicio();
                servicio.setId_servicio(UUID.randomUUID());
                servicio.setNombre(e.text());
                servicio.setAdicional(false);
                servicios.add(servicio);
            }
        }
        for(Element e:doc.select("div.servicios-modal>ul.adicionales-modal>li")) {
            if(!e.text().contains(" Adicionales")) {
                Servicio servicio = new Servicio();
                servicio.setId_servicio(UUID.randomUUID());
                servicio.setNombre(e.text());
                servicio.setAdicional(false);
                servicios.add(servicio);
            }
        }
        return servicios;
    }

    private Set<Imagen> getImagenes(Document doc) {
        Set<Imagen> imagenes = new HashSet<>();
        Elements elements = doc.select("ul.ficha-galeria>li");
        for(Element e:elements) {
            Imagen imagen = new Imagen();
            imagen.setId_imagen(UUID.randomUUID());
            imagen.setUrl(e.attr("data-src"));
            imagenes.add(imagen);
        }
        return imagenes;
    }

    private static String getCartel(Document doc) {
        return doc.location().substring(8, doc.location().indexOf(".cl/"));
    }

    private String getValor(Document doc) {
        String valor = doc.select("div.tarifa_info").text();
        if(valor.isEmpty()) {
            log.error("VALOR: No se encontró valor en: {}, para ts: {}", valor, getNombre(doc,"div.informacion-b1>div.nombre-perfil"));
        }
        return valor;
    }

    private String getEstatura(Document doc) {
        return doc.select("li[aria-label=Estatura:]").text();
    }

    private static String getTelefono(Document doc) {
        return doc.select("div.text-1>a#llamar").text();
    }

    private static String getHorario(Document doc) {
        return doc.select("div.horario_info>div.text-1>p").text();
    }

    private static String getText(Document doc) {
        return doc.select("p.sobreMi-content_text").text();
    }

    private static String getUbicacion(Document doc) {
        return doc.select("div.ubicacion_info>div.text-1").text();
    }

    private static String getDepilacion(Document doc) {
        return doc.select("li[aria-label=Depilación:]").text().replaceAll("[a-z ñ]", "");
    }

    private static Integer getIdpagina(Document doc) {
        return Integer.valueOf(doc.location().replaceAll("[a-zA-z:./-]", ""));
    }

    private static String getMedidas(Document doc, String selector) {
        return doc.select(selector).text().replaceAll("[a-z ñ.]", "");
    }

    static Integer getEdad(Document doc) {
        String strEdad = doc.select("li[aria-label=Edad:]").text().replaceAll("[a-z ñ]", "");
        if (strEdad.isEmpty()) {
            return 0;
        }
        Integer edad = null;
        try {
            edad = Integer.valueOf(strEdad);
        } catch (NumberFormatException e) {
            log.error("EDAD: No se pudo leer la Edad en: {} ts: {}", strEdad, getNombre(doc, "div.informacion-b1>div.nombre-perfil"));
            log.error(e.getMessage());
        }
        return edad;
    }

    private static String getNombre(Document doc, String selector) {
        return doc.select(selector).textNodes().get(0).text();
    }
}
