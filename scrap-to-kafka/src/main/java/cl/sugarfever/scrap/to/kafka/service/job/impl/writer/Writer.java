package cl.sugarfever.scrap.to.kafka.service.job.impl.writer;

import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.scrap.to.kafka.service.service.TsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writer implements ItemWriter<Ts> {

    private static final Logger log = LoggerFactory.getLogger(Writer.class);

    @Autowired
    private TsService tsService;

    @Override
    public void write(List<? extends Ts> ts) {
        tsService.save((List<Ts>) ts);
    }

}