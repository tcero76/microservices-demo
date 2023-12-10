package cl.sugarfever.scrap.to.kafka.service.service;


import cl.sugarfever.postgres.model.Ts;

import java.util.List;

public interface TsService {
    public void save(List<Ts> tses);
}
