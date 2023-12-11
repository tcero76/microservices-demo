package cl.sugarfever.postgres.model;

import cl.sugarfever.outbox.OutboxStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "outbox", schema = "catalogo")
@Entity
public class Outbox implements Serializable {
    @Id
    private UUID id_outbox;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "processed_at")
    private Timestamp processedAt;
    @Column(name = "payload")
    private String payload;
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;
    @Version
    private int version;
}
