package cl.sugarfever.elastic.query.client.service;

import cl.sugarfever.elastic.IndexModel;
import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ElasticQueryService<T extends IndexModel> {
    List<T> getDocumentByText(String text);
}
