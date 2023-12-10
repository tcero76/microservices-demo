package cl.sugarfever.outbox.to.kafka.outbox.message;

import cl.sugarfever.kafka.avro.Ts;
import cl.sugarfever.postgres.outbox.Imagen;
import cl.sugarfever.postgres.outbox.Servicio;
import cl.sugarfever.postgres.outbox.TsScrapEventPayload;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TsMessageMapper {

    public Ts mapToTsAvroModel(TsScrapEventPayload tsScrapEventPayload) {
        return Ts.newBuilder()
                .setAtencion(tsScrapEventPayload.getAtencion())
                .setCartel(tsScrapEventPayload.getCartel())
                .setCiudad(tsScrapEventPayload.getCiudad())
                .setDepilacion(tsScrapEventPayload.getDepilacion())
                .setDescripcion(tsScrapEventPayload.getDescripcion())
                .setEdad(tsScrapEventPayload.getEdad())
                .setEstatura(tsScrapEventPayload.getEstatura())
                .setFecharegistro(tsScrapEventPayload.getFecharegistro())
                .setHorario(tsScrapEventPayload.getHorario())
                .setIdjob(tsScrapEventPayload.getIdjob())
                .setIdpagina(tsScrapEventPayload.getIdpagina())
                .setIdTs(tsScrapEventPayload.getId_ts().toString())
                .setImagen(tsScrapEventPayload.getImagen())
                .setImagenes(tsScrapEventPayload.getImagenes().stream().map(imagen -> mapToImagenAvroModel(imagen)).collect(Collectors.toList()))
                .setMedidas(tsScrapEventPayload.getMedidas())
                .setNombre(tsScrapEventPayload.getNombre())
                .setSeccion(tsScrapEventPayload.getSeccion())
                .setServicios(tsScrapEventPayload.getServicios().stream().map(servicio -> mapToServicioAvroModel(servicio)).collect(Collectors.toList()))
                .setSexo(tsScrapEventPayload.getSexo())
                .setSitio(tsScrapEventPayload.getSitio())
                .setTelefono(tsScrapEventPayload.getTelefono())
                .setUbicacion(tsScrapEventPayload.getUbicacion())
                .setValor(tsScrapEventPayload.getValor())
                .setVideo(tsScrapEventPayload.getVideo())
                .build();
    }

    private cl.sugarfever.kafka.avro.Servicio mapToServicioAvroModel(Servicio servicio) {
        return cl.sugarfever.kafka.avro.Servicio.newBuilder()
                .setAdicional(servicio.getAdicional())
                .setNombre(servicio.getNombre())
                .setIdservicio(servicio.getId_servicio().toString())
                .build();
    }

    private cl.sugarfever.kafka.avro.Imagen mapToImagenAvroModel(Imagen imagen) {
        return cl.sugarfever.kafka.avro.Imagen.newBuilder()
                .setIdimagen(imagen.getId_imagen().toString())
                .setUrl(imagen.getUrl())
                .build();
    }
}
