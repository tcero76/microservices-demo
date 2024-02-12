package cl.sugarfever.scrap.to.kafka.service;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(basePackages = "cl.sugarfever")
@EntityScan(basePackages = {"cl.sugarfever.postgres.model"})
@EnableJpaRepositories(basePackages = {"cl.sugarfever.postgres.repo"})
public class ScrapToPostegresApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScrapToPostegresApplication.class, args);
	}
}
