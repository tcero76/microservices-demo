package cl.sugarfever.elastic.index.client.repository;

import cl.sugarfever.elastic.model.ScrapIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapElasticsearchIndexRepository extends ElasticsearchRepository<ScrapIndexModel, String> {
}
