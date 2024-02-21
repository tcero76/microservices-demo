package cl.sugarfever.kafka.to.elastic.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cl.sugarfever.kafka", "cl.sugarfever.elastic", "cl.sugarfever.config"})
public class KafkaToElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticApplication.class, args);
    }
}
