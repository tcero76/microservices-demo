package cl.sugarfever.config.data.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "scrap-to-kafka")
public class ScrapToKafkaServiceConfigData {
    private String schedulerFixedRate;
    private String schedulerInitialDelay;
}
