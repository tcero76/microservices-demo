package cl.sugarfever.reactive.elastic.query.service.business;

import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryService {
    Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text);
}
