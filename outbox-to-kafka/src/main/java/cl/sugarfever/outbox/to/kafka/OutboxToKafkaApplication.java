package cl.sugarfever.outbox.to.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"cl.sugarfever.outbox.to.kafka", "cl.sugarfever.postgres.config",
        "cl.sugarfever.config.data.service.config", "cl.sugarfever.kafka", "cl.sugarfever.scheduler"})
@EntityScan(basePackages = {"cl.sugarfever.postgres.model"})
@EnableJpaRepositories(basePackages = {"cl.sugarfever.postgres.repo"})
public class OutboxToKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutboxToKafkaApplication.class);
    }
}
