package cl.sugarfever.postgres.repo;

import cl.sugarfever.outbox.OutboxStatus;
import cl.sugarfever.postgres.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostgresCatalogoOutbox extends JpaRepository<Outbox, UUID> {
    List<Outbox> findByOutboxStatus(OutboxStatus outbox_status);
}
