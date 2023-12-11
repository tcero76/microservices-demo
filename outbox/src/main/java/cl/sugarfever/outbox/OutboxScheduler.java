package cl.sugarfever.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}