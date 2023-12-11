package cl.sugarfever.kafka.producer.service.impl;

import cl.sugarfever.kafka.producer.service.KafkaProducer;
import cl.sugarfever.outbox.OutboxStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.function.BiConsumer;

@Service
@AllArgsConstructor
@Slf4j
public class ScrapKafkaProducer<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K,V> {

    private final ObjectMapper mapper;
    private final KafkaTemplate<String,V> kafkaTemplate;
    @Override
    public void send(String topicName, String key, K event, V message, BiConsumer<K, OutboxStatus> callback) {
        log.info("Sending key='{}' to topic='{}'", key, topicName);
        ListenableFuture<SendResult<String, V>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
        addCallback(topicName, key, event, kafkaResultFuture, callback);
    }
    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
    private void addCallback(String topicName, String key, K event,
                             ListenableFuture<SendResult<String,V>> kafkaResultFuture, BiConsumer<K,OutboxStatus> callback) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<SendResult<String, V>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", key, topicName, throwable);
                callback.accept(event, OutboxStatus.FAILED);
            }
            @Override
            public void onSuccess(SendResult<String, V> longTsSendResult) {
                RecordMetadata metadata = longTsSendResult.getRecordMetadata();
                log.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
                callback.accept(event, OutboxStatus.COMPLETED);
            }
        });
    }

    public <T> T getPayload(String payload, Class<T> type) {
        try {
            return mapper.readValue(payload, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
