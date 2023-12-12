package cl.sugarfever.scrap.to.kafka.service.scheduler;

import cl.sugarfever.scheduler.Scheduler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@AllArgsConstructor
public class JobScheduler implements Scheduler {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    @Scheduled(fixedDelayString = "${scrap-to-kafka.scheduler.fixed.rate}",
            initialDelayString = "${scrap-to-kafka.scheduler.initial.delay}")
    public void process() {
        log.info("Successful Job Execution!");
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
