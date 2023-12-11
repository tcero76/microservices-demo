package cl.sugarfever.kafka.producer.service;

import cl.sugarfever.outbox.OutboxStatus;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;
import java.util.function.BiConsumer;

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {
    void send(String topicName, String key, K event, V message, BiConsumer<K, OutboxStatus> callback);
}
