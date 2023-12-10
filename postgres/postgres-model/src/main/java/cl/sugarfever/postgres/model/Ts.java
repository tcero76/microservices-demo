package cl.sugarfever.postgres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ts", schema = "catalogo")
@Entity
public class Ts implements Serializable {

    @NotNull(message = " Id_ts:Valor no puede ser vacío")
    @Id
    private UUID id_ts;
    @NotEmpty(message = " Cartel:Valor no puede ser vacío")
    @Column(name = "cartel")
    private String cartel;
    @NotNull(message = "Valor ;:no puede ser vacío")
    @Column(name = "idpagina")
    private int idpagina;
    @Column(name = "sexo")
    private String sexo;
    @NotNull(message = "Edad: Valor no puede ser vacío")
    @Column(name = "edad")
    private int edad;
    @NotEmpty(message = "Nombre:Valor no puede ser vacío")
    @Column(name = "nombre")
    private String nombre;
    @NotEmpty(message = "Medidas:Valor no puede ser vacío")
    @Column(name = "medidas")
    private String medidas;
    @Column(name = "seccion")
    private String seccion;
    @NotEmpty(message = "Depilacion:Valor no puede ser vacío")
    @Column(name = "depilacion")
    private String depilacion;
    @NotEmpty(message = "Ubicacion:Valor no puede ser vacío")
    @Column(name = "ubicacion")
    private String ubicacion;
    @NotEmpty(message = "Horario:Valor no puede ser vacío")
    @Column(name = "horario")
    private String horario;
    @NotEmpty(message = "Descripcion:Valor no puede ser vacío")
    @Column(name = "descripcion")
    private String descripcion;
    @NotNull(message = "Atencion:Valor no puede ser vacío")
    @Column(name = "atencion")
    private String atencion;
    @Column(name = "telefono")
    private String telefono;
    @NotEmpty(message = "Estatura:Valor no puede ser vacío")
    @Column(name = "estatura")
    private String estatura;
    @Column(name = "valor")
    private String valor;
    @Column(name = "video")
    private String video;
    @NotEmpty(message = " Imagen:Valor no puede ser vacío")
    @Column(name = "imagen")
    private String imagen;
    @NotNull(message = "Idjob: Valor no puede ser vacío")
    @Column(name = "idjob")
    private long idjob;
    @NotEmpty(message = "Sitio:Valor no puede ser vacío")
    @Column(name = "sitio")
    private String sitio;
    @NotEmpty(message = "Ciudad:Valor no puede ser vacío")
    @Column(name = "ciudad")
    private String ciudad;
//    @NotEmpty(message = "Servicios: Valor no puede ser vacío")
    @OneToMany(mappedBy = "ts", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    private Set<Servicio> servicios;
//    @NotEmpty(message = "Imagenes: Valor no puede ser vacío")
    @OneToMany(mappedBy = "ts", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    private Set<Imagen> imagenes;
    @NotNull(message = "Fecharegistro: Valor no puede ser vacío")
    @Column(name = "fecharegistro")
    private long fecharegistro;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ts ts = (Ts) o;
        return idpagina == ts.idpagina && Objects.equals(cartel, ts.cartel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartel, idpagina);
    }
}
