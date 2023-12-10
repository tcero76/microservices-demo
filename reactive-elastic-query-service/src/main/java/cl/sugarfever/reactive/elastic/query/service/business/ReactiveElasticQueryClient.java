package cl.sugarfever.reactive.elastic.query.service.business;

import cl.sugarfever.elastic.model.ScrapIndexModel;
import reactor.core.publisher.Flux;

public interface ReactiveElasticQueryClient {
    Flux<ScrapIndexModel> getIndexModelByText(String text);
}
