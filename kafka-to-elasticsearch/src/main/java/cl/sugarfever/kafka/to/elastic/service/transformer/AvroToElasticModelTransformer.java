package cl.sugarfever.kafka.to.elastic.service.transformer;

import cl.sugarfever.elastic.model.Imagen;
import cl.sugarfever.elastic.model.ScrapIndexModel;
import cl.sugarfever.kafka.avro.Servicio;
import cl.sugarfever.kafka.avro.Ts;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class AvroToElasticModelTransformer {

    public List<ScrapIndexModel> getElasticModels(List<Ts> tses) {
        return tses.stream()
                .map(ts -> ScrapIndexModel
                        .builder()
                        .idpagina(ts.getIdpagina())
                        .edad(ts.getEdad())
                        .atencion(ts.getAtencion())
                        .cartel(ts.getCartel())
                        .ciudad(ts.getCiudad())
                        .idjob(ts.getIdjob())
                        .depilacion(ts.getDepilacion())
                        .descripcion(ts.getDescripcion())
                        .horario(ts.getHorario())
                        .medidas(ts.getMedidas())
                        .sexo(ts.getSexo())
                        .sitio(ts.getSitio())
                        .estatura(ts.getEstatura())
                        .fecharegistro(ts.getFecharegistro())
                        .seccion(ts.getSeccion())
                        .valor(ts.getValor())
                        .telefono(ts.getTelefono())
                        .ubicacion(ts.getUbicacion())
                        .nombre(ts.getNombre())
                        .imagenes(avroImagenesToElasticModel(ts.getImagenes()))
                        .servicios(avroServiciosToElasticModel(ts.getServicios()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<cl.sugarfever.elastic.model.Servicio> avroServiciosToElasticModel(List<Servicio> servicios) {
        return servicios.stream()
                .map(servicio -> cl.sugarfever.elastic.model.Servicio.builder()
                .adicional(servicio.getAdicional())
                .nombre(servicio.getNombre())
                .idservicio(servicio.getIdservicio())
                .build()
        ).collect(Collectors.toList());
    }

    private List<Imagen> avroImagenesToElasticModel(List<cl.sugarfever.kafka.avro.Imagen> imagenes) {
        return imagenes.stream().map(imagen -> Imagen.builder()
                .url(imagen.getUrl())
                .idimagen(imagen.getIdimagen())
                .build()
        ).collect(Collectors.toList());
    }

    ;
}
