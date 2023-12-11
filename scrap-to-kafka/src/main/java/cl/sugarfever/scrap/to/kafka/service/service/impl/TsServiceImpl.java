package cl.sugarfever.scrap.to.kafka.service.service.impl;

import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.postgres.services.UpdateTs;
import cl.sugarfever.postgres.mapper.TsMapper;
import cl.sugarfever.scrap.to.kafka.service.service.TsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class TsServiceImpl implements TsService {
    private final UpdateTs<Ts> postgresService;
    @Override
    @Transactional
    public void save(List<Ts> tses) {
        postgresService.deleteTsesDropped(tses);
        tses.forEach(ts -> {
            postgresService.update(ts);
        });
    }
}
