package cl.sugarfever.scrap.to.kafka.service.service;

import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.postgres.repo.PostgresTsRepo;
import cl.sugarfever.postgres.services.UpdateTs;
import cl.sugarfever.postgres.services.impl.PostgresUpdateTs;
import cl.sugarfever.scrap.to.kafka.service.TceroApplication;
import cl.sugarfever.scrap.to.kafka.service.service.datagen.TsGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TceroApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Slf4j
public class TestValid {
    @Autowired
    private PostgresUpdateTs postgresUpdateTs;
    @Autowired
    private PostgresTsRepo postgresTsRepo;

    @Before
    public void init() {
        List<Ts> all = postgresTsRepo.findAll();
        postgresTsRepo.deleteAll(all);
        TsGenerator tsGenerator = new TsGenerator();
        List<Ts> tses = Arrays.asList(
                tsGenerator.getTs("escortnorte",1),
                tsGenerator.getTs("planetaescort",2),
                tsGenerator.getTs("laplayaescort",3),
                tsGenerator.getTs("escortnorte",4),
                tsGenerator.getTs("laplayaescort",5)
        );
//        List<Ts> ts = postgresUpdateTs.update(tses);
//        log.info("Ts: Hay {}", ts.size());
    }
    @Test
    public void borrarTest(){
        TsGenerator tsGenerator = new TsGenerator();
        List<Ts> tses = Arrays.asList(
                tsGenerator.getTs("escortnorte",1),
                tsGenerator.getTs("planetaescort",2),
                tsGenerator.getTs("laplayaescort",3),
                tsGenerator.getTs("escortnorte",4),
                tsGenerator.getTs("laplayaescort",5)
        );
        tses.get(0).setAtencion(null);
        tses.get(0).setImagen(null);
//        postgresUpdateTs.update(tses);
        Assert.assertEquals(5, tses.size());
    }
}
