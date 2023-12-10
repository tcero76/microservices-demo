package cl.sugarfever.scrap.to.kafka.service;

import cl.sugarfever.config.data.service.config.ScrapToKafkaServiceConfigData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
@ComponentScan(basePackages = "cl.sugarfever")
@EntityScan(basePackages = {"cl.sugarfever.postgres.model"})
@EnableJpaRepositories(basePackages = {"cl.sugarfever.postgres.repo"})
@EnableScheduling
@Slf4j
public class TceroApplication {

	public static void main(String[] args) {
		SpringApplication.run(TceroApplication.class, args);
	}

	public TceroApplication(JobLauncher jobLauncher, Job job, ScrapToKafkaServiceConfigData scrapToKafkaServiceConfigData) {
		this.jobLauncher = jobLauncher;
		this.job = job;
	}
	private final JobLauncher jobLauncher;
	private final Job job;

    @Scheduled(fixedDelayString = "${scrap-to-kafka.scheduler.fixed.rate}", initialDelayString = "${scrap-to-kafka.scheduler.initial.delay}")
    public void triggerJob() {
		log.info("Successful Job Execution! whit delay {} and initial {}");
		try {
            jobLauncher
                    .run(job,  new JobParametersBuilder()
                    .addDate("date", new Date())
                    .toJobParameters());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
