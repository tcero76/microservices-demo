package cl.sugarfever.scrap.to.kafka.service.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(ScrapTest.Config.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ScrapTest {
    @Configuration
    static class Config {
        @Bean
        public Integer getValue() {
            return 1;
        }
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        System.out.println(applicationContext);
    }

//@Bean
//public Step step1(
//        ItemReader<BookRecord> csvItemReader, ItemWriter<Book> jsonItemWriter) throws IOException {
//    return stepBuilderFactory
//            .get("step1")
//            .<BookRecord, Book> chunk(3)
//            .reader(csvItemReader)
//            .processor(bookItemProcessor())
//            .writer(jsonItemWriter)
//            .build();
//}
}
