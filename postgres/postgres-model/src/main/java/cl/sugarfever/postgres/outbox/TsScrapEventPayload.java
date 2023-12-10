package cl.sugarfever.postgres.outbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TsScrapEventPayload {

    @JsonProperty
    private UUID id_ts;
    @JsonProperty
    private String cartel;
    @JsonProperty
    private int idpagina;
    @JsonProperty
    private String sexo;
    @JsonProperty
    private int edad;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String medidas;
    @JsonProperty
    private String seccion;
    @JsonProperty
    private String depilacion;
    @JsonProperty
    private String ubicacion;
    @JsonProperty
    private String horario;
    @JsonProperty
    private String descripcion;
    @JsonProperty
    private String atencion;
    @JsonProperty
    private String telefono;
    @JsonProperty
    private String estatura;
    @JsonProperty
    private String valor;
    @JsonProperty
    private String video;
    @JsonProperty
    private String imagen;
    @JsonProperty
    private long idjob;
    @JsonProperty
    private String sitio;
    @JsonProperty
    private String ciudad;
    @JsonProperty
    private Set<Servicio> servicios;
    @JsonProperty
    private Set<Imagen> imagenes;
    @JsonProperty
    private long fecharegistro;
}
