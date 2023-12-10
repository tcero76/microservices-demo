package cl.sugarfever.scrap.to.kafka.service.service;

import cl.sugarfever.postgres.model.Outbox;
import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.postgres.repo.PostgresCatalogoOutbox;
import cl.sugarfever.postgres.repo.PostgresTsRepo;
import cl.sugarfever.postgres.services.UpdateTs;
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
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TceroApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Slf4j
public class TsTesting {
    @Autowired
    private UpdateTs<Ts> postgresService;
    @Autowired
    private PostgresTsRepo postgresTsRepo;
    @Autowired
    private PostgresCatalogoOutbox postgresCatalogoOutbox;
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
        tses.stream().forEach(ts -> {
            postgresService.update(ts);
        });
        log.info("Ts: se almacenó {}", tses.size());
    }
    @Test
    public void save() {
        List<Ts> all = postgresTsRepo.findAll();
        TsGenerator tsGenerator = new TsGenerator();
        List<Ts> tses = Arrays.asList(
                tsGenerator.getTs("escortnorte",1),
                tsGenerator.getTs("planetaescort",6),
                tsGenerator.getTs("laplayaescort",3),
                tsGenerator.getTs("escortnorte",4),
                tsGenerator.getTs("laplayaescort",5)
        );
        postgresService.deleteTsesDropped(tses);
        tses.stream().forEach(ts -> {
            postgresService.update(ts);
        });
        log.info("Ts: Se actualizaron {} registros más", tses.size());
        List<Ts> postgresTsRepoAll = postgresTsRepo.findAll();
        log.info("Los ids que quedaron son: {}", postgresTsRepoAll.stream().map(ts -> ts.getId_ts().toString()).collect(Collectors.toList()));
        Assert.assertEquals(5, postgresTsRepoAll.size());
        List<Outbox> catalogoOutboxAll = postgresCatalogoOutbox.findAll();
        Assert.assertEquals(10, catalogoOutboxAll.size());
        Set<Imagen> imagens = postgresTsRepoAll.stream().flatMap(ts -> ts.getImagenes().stream()).collect(Collectors.toSet());
        Set<Servicio> servicios = postgresTsRepoAll.stream().flatMap(ts -> ts.getServicios().stream()).collect(Collectors.toSet());
        Assert.assertEquals(25,servicios.size());
        Assert.assertEquals(25, imagens.size());
    }

}
