package cl.sugarfever.reactive.elastic.query.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class WebSecurityConfig extends AbstractReactiveElasticsearchConfiguration {
        @Override
        public ReactiveElasticsearchClient reactiveElasticsearchClient() {
            final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                    .connectedTo("172.28.0.3:9200")
                    .build();
            return ReactiveRestClients.create(clientConfiguration);
        }
}
