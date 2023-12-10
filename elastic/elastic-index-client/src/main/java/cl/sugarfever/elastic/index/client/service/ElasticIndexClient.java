package cl.sugarfever.elastic.index.client.service;

import cl.sugarfever.elastic.IndexModel;
import cl.sugarfever.elastic.model.ScrapIndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    public List<String> save(List<T> documents);
}
