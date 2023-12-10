package cl.sugarfever.elastic.model;

import cl.sugarfever.elastic.IndexModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@Document(indexName = "#{elasticConfigData.indexName}")
public class ScrapIndexModel implements IndexModel {
    @JsonProperty
    private String id;
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
    private String cartel;
    @JsonProperty
    private String ciudad;
    @Field(type = FieldType.Nested)
    private List<Servicio> servicios;
    @Field(type = FieldType.Nested)
    private List<Imagen> imagenes;
    @JsonProperty
    private long fecharegistro;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScrapIndexModel that = (ScrapIndexModel) o;
        return idpagina == that.idpagina && Objects.equals(cartel, that.cartel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpagina, cartel);
    }
}
