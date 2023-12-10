package cl.sugarfever.reactive.elastic.query.service.business.impl;

import cl.sugarfever.config.data.service.config.ElasticQueryServiceConfigData;
import cl.sugarfever.elastic.model.ScrapIndexModel;
import cl.sugarfever.reactive.elastic.query.service.business.ReactiveElasticQueryClient;
import cl.sugarfever.reactive.elastic.query.service.repository.ElasticQueryRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@AllArgsConstructor
public class ScrapReactiveElasticQueryClient implements ReactiveElasticQueryClient {

    private static final Logger Log = LoggerFactory.getLogger(ScrapReactiveElasticQueryClient.class);

    private final ElasticQueryRepository elasticQueryRepository;

    private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

    @Override
    public Flux<ScrapIndexModel> getIndexModelByText(String text) {
        return elasticQueryRepository
                .findByDescripcion(text)
                .delayElements(Duration.ofMillis(elasticQueryServiceConfigData.getBackPressureDelayMs()));
    }
}
