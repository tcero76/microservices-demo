package cl.sugarfever.postgres.mapper;

import cl.sugarfever.postgres.outbox.Imagen;
import cl.sugarfever.postgres.outbox.Servicio;
import cl.sugarfever.postgres.outbox.TsScrapEventPayload;
import cl.sugarfever.postgres.model.Ts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TsMapper {

    public TsScrapEventPayload mapToTsScrapEventPayload(Ts ts) {
        return TsScrapEventPayload.builder()
                .atencion(ts.getAtencion())
                .cartel(ts.getCartel())
                .ciudad(ts.getCiudad())
                .depilacion(ts.getDepilacion())
                .descripcion(ts.getDescripcion())
                .edad(ts.getEdad())
                .estatura(ts.getEstatura())
                .fecharegistro(ts.getFecharegistro())
                .horario(ts.getHorario())
                .id_ts(ts.getId_ts())
                .idjob(ts.getIdjob())
                .idpagina(ts.getIdpagina())
                .imagen(ts.getImagen())
                .imagenes(mapToImagenEventPayload(ts.getImagenes()))
                .medidas(ts.getMedidas())
                .nombre(ts.getNombre())
                .seccion(ts.getSeccion())
                .sexo(ts.getSexo())
                .sitio(ts.getSitio())
                .servicios(mapToServiciosEventPayload(ts.getServicios()))
                .valor(ts.getValor())
                .video(ts.getVideo())
                .telefono(ts.getTelefono())
                .ubicacion(ts.getUbicacion())
                .build();
    }
    private Set<Servicio> mapToServiciosEventPayload(Set<cl.sugarfever.postgres.model.Servicio> servicios) {
        return servicios.stream()
                .map(servicio -> Servicio.builder()
                        .id_servicio(servicio.getId_servicio())
                        .nombre(servicio.getNombre())
                        .adicional(servicio.getAdicional())
                        .build())
                .collect(Collectors.toSet());
    }
    private Set<Imagen> mapToImagenEventPayload(Set<cl.sugarfever.postgres.model.Imagen> imagenes) {
        return imagenes.stream().map(imagen -> Imagen.builder()
                .id_imagen(imagen.getId_imagen())
                .url(imagen.getUrl())
                .build()).collect(Collectors.toSet());
    }
}
