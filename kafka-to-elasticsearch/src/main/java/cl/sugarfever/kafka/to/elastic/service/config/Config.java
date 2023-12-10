package cl.sugarfever.kafka.to.elastic.service.config;

import cl.sugarfever.config.data.service.config.KafkaToElasticServiceConfigData;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    private final KafkaToElasticServiceConfigData kafkaToElasticServiceConfigData;

    public Config(KafkaToElasticServiceConfigData kafkaToElasticServiceConfigData) {
        this.kafkaToElasticServiceConfigData = kafkaToElasticServiceConfigData;
    }
}
