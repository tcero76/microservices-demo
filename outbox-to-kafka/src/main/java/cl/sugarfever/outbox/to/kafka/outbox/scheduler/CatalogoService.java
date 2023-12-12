package cl.sugarfever.outbox.to.kafka.outbox.scheduler;

import cl.sugarfever.scheduler.Scheduler;
import cl.sugarfever.config.data.service.config.KafkaConfigData;
import cl.sugarfever.kafka.avro.Ts;
import cl.sugarfever.outbox.OutboxStatus;
import cl.sugarfever.outbox.to.kafka.outbox.message.TsMessageMapper;
import cl.sugarfever.postgres.model.Outbox;
import cl.sugarfever.postgres.outbox.TsScrapEventPayload;
import cl.sugarfever.postgres.repo.PostgresCatalogoOutbox;
import cl.sugarfever.kafka.producer.service.impl.ScrapKafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CatalogoService implements Scheduler {

    private final TsMessageMapper tsMessageMapper;
    private final PostgresCatalogoOutbox postgresCatalogoOutbox;
    private final KafkaConfigData kafkaConfigData;
    private final ScrapKafkaProducer<Outbox, Ts> scrapKafkaProducer;
    @Override
    @Scheduled(fixedDelayString = "${outbox-to-kafka.scheduler-fixed-rate}",
            initialDelayString = "${outbox-to-kafka.scheduler-initial-delay}")
    public void process() {
        List<Outbox> events = postgresCatalogoOutbox.findByOutboxStatus(OutboxStatus.STARTED);
        log.info("CatalogoOutbox: número iniciado: {}", events.size());
        events.forEach(event -> {
            TsScrapEventPayload payload = scrapKafkaProducer
                .getPayload(event.getPayload(), TsScrapEventPayload.class);
                scrapKafkaProducer.send(
                    kafkaConfigData.getTopicName(),
                    event.getId_outbox().toString(),
                    event,
                    tsMessageMapper.mapToTsAvroModel(payload),
                    this::updateOutBoxStatus
                );
        });
    }

    private void updateOutBoxStatus(Outbox outbox, OutboxStatus outboxStatus) {
            outbox.setOutboxStatus(outboxStatus);
            postgresCatalogoOutbox.save(outbox);
    }
}
