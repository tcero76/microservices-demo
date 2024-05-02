package cl.sugarfever.postgres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "serviciohistory", schema = "catalogo")
@Entity
public class sh {
    @Id
    private UUID id_servicio;
    @Column(name = "adicional")
    private Boolean adicional;
    @Column(name="nombre")
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ts")
    private History ts;
}
