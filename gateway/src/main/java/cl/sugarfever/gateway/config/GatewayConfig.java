package cl.sugarfever.gateway.config;

import cl.sugarfever.config.data.service.config.GatewayServiceConfigData;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GatewayConfig {
    private final GatewayServiceConfigData gatewayServiceConfigData;

    public GatewayConfig(GatewayServiceConfigData gatewayServiceConfigData) {
        this.gatewayServiceConfigData = gatewayServiceConfigData;
    }

    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        return reactiveResilience4JCircuitBreakerFactory ->
                reactiveResilience4JCircuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                            .timeLimiterConfig(TimeLimiterConfig.custom()
                            .timeoutDuration(Duration.ofMillis(gatewayServiceConfigData.getTimeoutMs()))
                            .build())
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .failureRateThreshold(gatewayServiceConfigData.getFailureRateThreshold())
                            .slowCallDurationThreshold(Duration.ofMillis(gatewayServiceConfigData.getSlowCallDurationThreshold()))
                            .slowCallRateThreshold(gatewayServiceConfigData.getSlowCallRateThreshold())
                            .permittedNumberOfCallsInHalfOpenState(gatewayServiceConfigData.getPermittedNumOfCallsInHalfOpenState())
                            .slidingWindowSize(gatewayServiceConfigData.getSlidingWindowSize())
                            .minimumNumberOfCalls(gatewayServiceConfigData.getMinNumberOfCalls())
                            .waitDurationInOpenState(Duration.ofMillis(gatewayServiceConfigData.getWaitDurationInOpenState()))
                        .build())
                    .build());
    }
}
