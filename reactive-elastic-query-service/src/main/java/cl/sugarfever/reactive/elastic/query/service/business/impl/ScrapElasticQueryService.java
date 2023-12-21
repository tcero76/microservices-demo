package cl.sugarfever.elastic.query.client.service.impl;

import cl.sugarfever.elastic.model.ScrapIndexModel;
import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import cl.sugarfever.elastic.query.service.common.transformer.ElasticQueryServiceResponseTransformer;
import cl.sugarfever.elastic.query.client.service.ElasticQueryService;
import cl.sugarfever.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@AllArgsConstructor
public class ScrapElasticQueryService implements ElasticQueryService<ScrapIndexModel> {
    private final ReactiveElasticQueryClient reactiveElasticQueryClient;
    private final ElasticQueryServiceResponseTransformer elasticQueryServiceResponseTransformer;

    @Override
    public List<ScrapIndexModel> getDocumentByText(String text) {
        return reactiveElasticQueryClient
                .getIndexModelByText(text)
                .map(elasticQueryServiceResponseTransformer::getResponseModel);
    }
}
