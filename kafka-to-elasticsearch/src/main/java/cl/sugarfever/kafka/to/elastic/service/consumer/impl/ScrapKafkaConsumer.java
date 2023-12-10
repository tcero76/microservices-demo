package cl.sugarfever.kafka.to.elastic.service.consumer.impl;

import cl.sugarfever.config.data.service.config.KafkaConfigData;
import cl.sugarfever.elastic.index.client.service.ElasticIndexClient;
import cl.sugarfever.elastic.model.ScrapIndexModel;
import cl.sugarfever.kafka.admin.client.KafkaAdminClient;
import cl.sugarfever.kafka.admin.config.KafkaAdminConfig;
import cl.sugarfever.kafka.avro.Ts;
import cl.sugarfever.kafka.to.elastic.service.consumer.KafkaConsumer;
import cl.sugarfever.kafka.to.elastic.service.transformer.AvroToElasticModelTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ScrapKafkaConsumer implements KafkaConsumer<Ts> {
    private final AvroToElasticModelTransformer avroToElasticModelTransformer;
    private final ElasticIndexClient<ScrapIndexModel> elasticIndexClient;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfigData;
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event) {
        kafkaAdminClient.checkTopicsCreated();
        log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
        kafkaListenerEndpointRegistry.getListenerContainer("scrapTopicListener").start();
    }

    @Override
    @KafkaListener(id = "scrapTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<Ts> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<UUID> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
                        "sending it to elastic: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());
        List<ScrapIndexModel> elasticModels = avroToElasticModelTransformer.getElasticModels(messages);
        elasticIndexClient.save(elasticModels);
    }
}
