package cl.sugarfever.scrap.to.kafka.service.job.impl.process;

import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.scrap.to.kafka.service.listener.JobListener;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Qualifier("StandardProcess")
@Slf4j
public class process implements ItemProcessor<Document, Ts> {
    @Autowired
    private JobListener jobExecutionListener;

    @Override
    public Ts process(Document doc) throws Exception {
        String str = doc.body().text();
        JSONObject obj = new JSONObject(str);
        String video = "";
        try {
            video = obj.getString("video");
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        Set<Servicio> servicios = new HashSet<Servicio>();
        JSONArray serviciosJson = (JSONArray) obj.get("servicios");
        for (Object s : serviciosJson) {
            servicios.add(Servicio.builder()
                    .id_servicio(UUID.randomUUID())
                    .nombre((String)s)
                    .adicional(false)
                    .build());
        }
        JSONArray adicionalesJson = (JSONArray) obj.get("adicionales");
        for (Object s : adicionalesJson) {
            servicios.add(Servicio.builder()
                    .id_servicio(UUID.randomUUID())
                    .nombre((String)s)
                    .adicional(true)
                    .build());
        }
        JSONArray imagenesJson = (JSONArray) obj.get("imagenes");
        Set<Imagen> imagenes = new HashSet<Imagen>();
        for (Object img : imagenesJson) {
            imagenes.add(Imagen.builder()
                    .id_imagen(UUID.randomUUID())
                    .url((String)img)
                    .build()
            );
        }
        String cartel = doc.location().substring(8,doc.location().indexOf(".cl/"));
        String ciudad = cartel.equals("laplayaescort")?"Viña del Mar":"Santiago";
        Ts ts = Ts.builder()
                .id_ts(UUID.randomUUID())
                .sitio(doc.location())
                .nombre(obj.getString("nombre"))
                .sexo(obj.getString("tipo"))
                .edad(obj.getInt("edad"))
                .medidas(obj.getString("medidas"))
                .seccion(obj.getString("seccion"))
                .idpagina(obj.getInt("id_ficha"))
                .depilacion(obj.getString("depilacion"))
                .ubicacion(obj.getString("ubicacion"))
                .horario(obj.getString("horario"))
                .descripcion(obj.getString("descripcion"))
                .atencion(obj.getString("atencion"))
                .estatura(obj.getString("estatura"))
                .valor(obj.getString("valor").replaceAll("[^0-9]", ""))
                .idjob(jobExecutionListener.getIdJob())
                .fecharegistro(new Date().getTime())
                .video(video)
                .imagen(obj.getString("imagen"))
                .servicios(servicios)
                .imagenes(imagenes)
                .telefono("")
                .sitio(doc.location())
                .cartel(cartel)
                .ciudad(ciudad)
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
}
