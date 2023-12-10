package cl.sugarfever.kafka.admin.client;

import cl.sugarfever.config.data.service.config.KafkaConfigData;
import cl.sugarfever.config.data.service.config.RetryConfigData;
import cl.sugarfever.kafka.admin.exception.KafkaClientException;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class KafkaAdminClient {
    private static Logger Log = LoggerFactory.getLogger(KafkaAdminClient.class);
    private final AdminClient adminClient;
    private final RetryConfigData retryConfigData;
    private final RetryTemplate retryTemplate;
    private final KafkaConfigData kafkaConfigData;

    public void checkTopicsCreated() {
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
            while (!isTopicCreated(topics, topic)) {
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTimeMs);
                sleepTimeMs *= multiplier;
                topics = getTopics();
            }
        }
    }
    private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
        Log.info("Creating {} topics(s), attempt {}", topicNames.size(), retryContext.getRetryCount());
        List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> new NewTopic(
                topic.trim(),
                kafkaConfigData.getNumOfPartitions(),
                kafkaConfigData.getReplicationFactor()
        )).collect(Collectors.toList());
        return adminClient.createTopics(kafkaTopics);
    }
    private Collection<TopicListing> getTopics() {
        Collection<TopicListing> topics;
        try {
            topics = retryTemplate.execute(this::doGetTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!", t);
        }
        return topics;
    }
    private void sleep(Long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error while sleeping for waiting new created topics!!");
        }
    }
    private boolean isTopicCreated(Collection<TopicListing> topics, String topicName) {
        if (topics == null) {
            return false;
        }
        return topics.stream().anyMatch(topic -> topic.name().equals(topicName));
    }
    private void checkMaxRetry(int retry, Integer maxRetry) {
        if (retry > maxRetry) {
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)!");
        }
    }

    private Collection<TopicListing> doGetTopics(RetryContext retryContext)
            throws ExecutionException, InterruptedException {
        Log.info("Reading kafka topic {}, attempt {}",
                kafkaConfigData.getTopicNamesToCreate().toArray(), retryContext.getRetryCount());
        Collection<TopicListing> topics = adminClient.listTopics().listings().get();
        if (topics != null) {
            topics.forEach(topic -> Log.debug("Topic with name {}", topic.name()));
        }
        return topics;
    }
}
