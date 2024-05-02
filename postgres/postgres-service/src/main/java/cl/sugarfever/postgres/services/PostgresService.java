package cl.sugarfever.postgres.services;

import cl.sugarfever.postgres.outbox.TsScrapEventPayload;
import cl.sugarfever.postgres.model.Outbox;
import cl.sugarfever.postgres.model.Ts;

import java.util.List;

public interface UpdateTs<T> {
    public Ts create(T ts);
    public void update(List<Ts> tses);
    public void deleteTsesDropped(List<Ts> ts);
    public Outbox outboxPersist(Ts tsSaved);
}

