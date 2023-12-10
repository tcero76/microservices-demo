package cl.sugarfever.postgres.outbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Servicio {
    @JsonProperty
    private UUID id_servicio;
    @JsonProperty
    private Boolean adicional;
    @JsonProperty
    private String nombre;
}
