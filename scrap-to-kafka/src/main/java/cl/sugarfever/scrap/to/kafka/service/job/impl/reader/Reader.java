package cl.sugarfever.scrap.to.kafka.service.job.impl.reader;


import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Component
@Qualifier("reader")
public class Reader implements ItemReader<Ts> {
    @Override
    public Ts read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Servicio servicio = Servicio.builder()
                .adicional(false)
                .nombre("servicial")
                .id_servicio(UUID.randomUUID())
                .build();

        Imagen imagen = Imagen.builder()
                .id_imagen(UUID.randomUUID())
                .url("http://fotitofotota.jpg")
                .build();

        Ts ts = Ts.builder()
                .idpagina(1)
                .sexo("nobinary")
                .imagen("carabonita")
                .nombre("Olga")
                .ciudad("chimbarongo")
                .telefono("666")
                .estatura("tilliblealta")
                .atencion("Muy atendido")
                .medidas("90-60-90")
                .horario("todo horario")
                .seccion("nobinari")
                .depilacion("brasileira")
                .ubicacion("por ahí por el centro")
                .descripcion("soy una putita muy bonita")
                .valor("buenobonitobaratito")
                .idjob(2345453453l)
                .sitio("web-eo")
                .edad(44)
                .cartel("Colonia")
                .servicios(Set.of(servicio))
                .imagenes(Set.of(imagen))
                .build();
        return ts;
    }
}
