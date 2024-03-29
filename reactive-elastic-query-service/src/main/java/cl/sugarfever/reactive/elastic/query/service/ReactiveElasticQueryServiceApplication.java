package cl.sugarfever.reactive.elastic.query.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cl.sugarfever.reactive", "cl.sugarfever.elastic", "cl.sugarfever.config" })
public class ReactiveElasticQueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveElasticQueryServiceApplication.class);
    }
}
