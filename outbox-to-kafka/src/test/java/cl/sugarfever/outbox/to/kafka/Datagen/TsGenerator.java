package cl.sugarfever.outbox.to.kafka.Datagen;

import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class TsGenerator {
    public Ts getTs(String cartel, Integer idpagina) {
        Set<Servicio> servicios = getServicios();
        Set<Imagen> imagenes = getImagenes();
        return Ts.builder()
                .id_ts(UUID.randomUUID())
                .cartel(cartel)
                .idpagina(idpagina)
                .edad(getInt())
                .atencion(getString())
                .ciudad("Santiago")
                .depilacion(getString())
                .descripcion(getString())
                .estatura(getString())
                .fecharegistro((new Date()).getTime())
                .horario(getString())
                .idjob((new Date()).getTime())
                .imagen(getString())
                .sexo(getString())
                .sitio(getString())
                .medidas(getString())
                .valor(getString())
                .video(getString())
                .nombre(getString())
                .servicios(new HashSet<>())
                .imagenes(new HashSet<>())
                .seccion(getString())
                .ubicacion(getString())
                .telefono(getString())
                .imagenes(imagenes)
                .servicios(servicios)
                .build();
    }

    private Set<Imagen> getImagenes() {
        return Set.of(
            new Imagen(UUID.randomUUID(), getString(), null),
            new Imagen(UUID.randomUUID(), getString(), null),
            new Imagen(UUID.randomUUID(), getString(), null),
            new Imagen(UUID.randomUUID(), getString(), null),
            new Imagen(UUID.randomUUID(), getString(), null)
        );
    }

    private Set<Servicio> getServicios() {
        return Set.of(
            new Servicio(UUID.randomUUID(), false, getString(), null),
            new Servicio(UUID.randomUUID(), false, getString(), null),
            new Servicio(UUID.randomUUID(), false, getString(), null),
            new Servicio(UUID.randomUUID(), false, getString(), null),
            new Servicio(UUID.randomUUID(), false, getString(), null)
        );
    }

    private static int getInt() {
        Random random = new Random();
        return random.nextInt();
    }

    public static String getString() {
        Random random = new Random();
        return random.ints(7, 9)
                .limit(7)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
