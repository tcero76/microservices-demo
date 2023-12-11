package cl.sugarfever.postgres.services;

import cl.sugarfever.outbox.OutboxStatus;
import cl.sugarfever.postgres.model.Outbox;

import java.util.List;

public interface OutboxQuery {

    List<Outbox> findAllByOutboxStatus(OutboxStatus outbox_status);
    List<Outbox> saveOutboxStatus(List<Outbox> catalogoOutboxes);
}
