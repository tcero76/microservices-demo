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
@Table(name = "imagenhistory", schema = "catalogo")
@Entity
public class ih {
    @Id
    private UUID id_imagen;
    @Column(name = "url")
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ts")
    private History ts;
}
