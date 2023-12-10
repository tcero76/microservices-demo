package cl.sugarfever.kafka.to.elastic.service.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;
import java.util.UUID;

public interface KafkaConsumer<T extends SpecificRecordBase> {

    void receive(List<T> messages, List<UUID> keys, List<Integer> partitions, List<Long> offsets);
}
