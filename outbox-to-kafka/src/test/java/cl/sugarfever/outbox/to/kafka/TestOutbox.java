package cl.sugarfever.outbox.to.kafka;

import cl.sugarfever.outbox.OutboxStatus;
import cl.sugarfever.outbox.to.kafka.Datagen.DataPayload;
import cl.sugarfever.postgres.model.Outbox;
import cl.sugarfever.postgres.repo.PostgresCatalogoOutbox;
import cl.sugarfever.postgres.services.OutboxQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Slf4j
public class TestOutbox {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PostgresCatalogoOutbox postgresCatalogoOutbox;

    @Autowired
    private OutboxQuery outboxQuery;

    private DataPayload data;
    @Before
    public void init() {
        data = new DataPayload(1, "Leonardo");
        String payload = null;
        try {
            payload = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Outbox> catalogoOutbox = Arrays.asList(
                new Outbox(UUID.randomUUID(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                        payload, OutboxStatus.STARTED, 0),
                new Outbox(UUID.randomUUID(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                        payload, OutboxStatus.COMPLETED, 0),
                new Outbox(UUID.randomUUID(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                        payload, OutboxStatus.FAILED, 0),
                new Outbox(UUID.randomUUID(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                        payload, OutboxStatus.STARTED, 0)
        );
        postgresCatalogoOutbox.saveAll(catalogoOutbox);
    }
    @Test
    public void readOutbox() {
        List<Outbox> allByOutboxStatus = outboxQuery.findAllByOutboxStatus(OutboxStatus.STARTED);
        log.info("OutboxStatus: tiene {} ", allByOutboxStatus.size());
        Assert.assertEquals(2, allByOutboxStatus.size());
        allByOutboxStatus.forEach(outboxQuery -> outboxQuery.setOutboxStatus(OutboxStatus.COMPLETED));
        outboxQuery.saveOutboxStatus(allByOutboxStatus);
        List<Outbox> all = outboxQuery.findAllByOutboxStatus(OutboxStatus.STARTED);
        log.info("OutboxStatus: ahora hay {}", all.size());
        Assert.assertEquals(0, all.size());
    }
}
