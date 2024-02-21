package cl.sugarfever.scrap.to.kafka.service.job;

import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.scrap.to.kafka.service.job.impl.process.EscortNorteProcess;
import cl.sugarfever.scrap.to.kafka.service.job.impl.process.RelaxProcess;
import cl.sugarfever.scrap.to.kafka.service.job.impl.reader.EscortNorteReader;
import cl.sugarfever.scrap.to.kafka.service.job.impl.reader.RelaxReader;
import cl.sugarfever.scrap.to.kafka.service.job.impl.reader.StandardReader;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class JobBean {
    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    private final StandardReader standardReader;
    public final ItemProcessor process;
    public final ItemWriter writer;
    private final JobExecutionListener jobExecutionListener;
    private final EscortNorteReader escortNorteReader;
    private final EscortNorteProcess escortNorteProcess;
    private final RelaxReader relaxReader;
    private final RelaxProcess relaxProcess;

    @Bean
    public Job JobScrapy() {
        Step estandarStep = stepBuilderFactory.get("estandarStep")
                .allowStartIfComplete(true)
                .<Document, Ts> chunk(500)
                .reader(standardReader)
                .processor(process)
                .writer(writer)
                .build();
        Step escortNorteStep = stepBuilderFactory.get("escortNorteStep")
                .<Document, Ts> chunk(500)
                .reader(escortNorteReader)
                .processor(escortNorteProcess)
                .writer(writer)
                .build();
        Step relaxStep = stepBuilderFactory.get("relax-step")
                .<Document,Ts>chunk(500)
                .reader(relaxReader)
                .processor(relaxProcess)
                .writer(writer)
                .build();
        Job job = jobBuilderFactory.get("accounting-job")
                .incrementer(new RunIdIncrementer())
                .start(relaxStep)
                .next(escortNorteStep)
                .next(estandarStep)
                .listener(jobExecutionListener)
                .build();
        return job;
    }

}
