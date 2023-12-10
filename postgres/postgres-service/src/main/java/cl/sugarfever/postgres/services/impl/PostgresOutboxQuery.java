package cl.sugarfever.postgres.services.impl;

import cl.sugarfever.outbox.OutboxStatus;
import cl.sugarfever.postgres.model.Outbox;
import cl.sugarfever.postgres.repo.PostgresCatalogoOutbox;
import cl.sugarfever.postgres.services.OutboxQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostgresOutboxQuery implements OutboxQuery {
    private final PostgresCatalogoOutbox postgresCatalogoOutbox;
    @Override
    public List<Outbox> findAllByOutboxStatus(OutboxStatus outboxStatus) {
        return postgresCatalogoOutbox.findByOutboxStatus(OutboxStatus.STARTED);

    }
    @Override
    public List<Outbox> saveOutboxStatus(List<Outbox> catalogoOutboxes) {
        return postgresCatalogoOutbox.saveAll(catalogoOutboxes);
    }

}
