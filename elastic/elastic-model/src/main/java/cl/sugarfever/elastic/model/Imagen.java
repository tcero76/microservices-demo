package cl.sugarfever.elastic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Imagen {
    @JsonProperty
    private String idimagen;
    @JsonProperty
    private String url;
}
