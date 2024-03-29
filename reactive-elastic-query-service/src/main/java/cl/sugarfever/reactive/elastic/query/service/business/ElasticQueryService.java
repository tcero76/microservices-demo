package cl.sugarfever.reactive.elastic.query.service.business;

import cl.sugarfever.elastic.IndexModel;
import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ElasticQueryService<T> {
    Flux<T> getDocumentByText(String text);
}
