package cl.sugarfever.scrap.to.kafka.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobListener.class);

    private Long idJob;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        idJob = (new Date()).getTime();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("BATCH: " + jobExecution.getJobId() + " - " + "Job completed succefully");
        }
    }

    public Long getIdJob() {
        return idJob;
    }
}
