package cl.sugarfever.elastic.query.client;

import cl.sugarfever.elastic.model.ScrapIndexModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ElasticQueryRepository extends ReactiveCrudRepository<ScrapIndexModel, String> {

    Flux<ScrapIndexModel> findByDescripcion(String text);
}
