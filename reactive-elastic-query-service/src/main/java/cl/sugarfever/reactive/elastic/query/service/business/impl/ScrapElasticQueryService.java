package cl.sugarfever.reactive.elastic.query.service.business.impl;

import cl.sugarfever.elastic.model.ScrapIndexModel;
import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import cl.sugarfever.elastic.query.service.common.transformer.ElasticQueryServiceResponseTransformer;
import cl.sugarfever.reactive.elastic.query.service.business.ElasticQueryService;
import cl.sugarfever.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@AllArgsConstructor
public class ScrapElasticQueryService implements ElasticQueryService<ElasticQueryServiceResponseModel> {
    private final ReactiveElasticQueryClient reactiveElasticQueryClient;
    private final ElasticQueryServiceResponseTransformer elasticQueryServiceResponseTransformer;

    @Override
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        return reactiveElasticQueryClient
                .getIndexModelByText(text)
                .map(elasticQueryServiceResponseTransformer::getResponseModel);
    }
}
