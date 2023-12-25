package cl.sugarfever.reactive.elastic.query.service.config;

import cl.sugarfever.config.data.service.config.ElasticConfigData;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@AllArgsConstructor
public class ReactiveElasticConfig extends AbstractReactiveElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;
    @Override
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        UriComponents serverUri = UriComponentsBuilder.fromHttpUrl(elasticConfigData.getConnectionUrl()).build();
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(serverUri.getHost()+":"+serverUri.getPort())
                .build();
        return ReactiveRestClients.create(clientConfiguration);
    }
}
