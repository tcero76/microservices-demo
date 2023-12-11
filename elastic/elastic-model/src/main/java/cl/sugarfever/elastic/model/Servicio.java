package cl.sugarfever.elastic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Servicio {
    @JsonProperty
    private String idservicio;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private boolean adicional;
}
