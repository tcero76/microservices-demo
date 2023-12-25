package cl.sugarfever.reactive.elastic.query.service.business.impl;

import cl.sugarfever.config.data.service.config.ElasticQueryServiceConfigData;
import cl.sugarfever.elastic.model.ScrapIndexModel;
import cl.sugarfever.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import cl.sugarfever.reactive.elastic.query.service.repository.ElasticQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@AllArgsConstructor
public class ScrapReactiveElasticQueryClient implements ReactiveElasticQueryClient {
    private final ElasticQueryRepository elasticQueryRepository;
    private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

    @Override
    public Flux<ScrapIndexModel> getIndexModelByText(String text) {
        return elasticQueryRepository
                .findByDescripcion(text)
                .delayElements(Duration.ofMillis(elasticQueryServiceConfigData.getBackPressureDelayMs()));
    }
}
