package cl.sugarfever.postgres.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "servicio", schema = "catalogo")
@Entity
public class Servicio {
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
