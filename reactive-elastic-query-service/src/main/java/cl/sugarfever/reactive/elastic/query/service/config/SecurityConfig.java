package cl.sugarfever.reactive.elastic.query.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webFluxSecurityConfig(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/documents/**").permitAll()
                .anyExchange()
                .permitAll()
                .and().csrf().disable()
                .build();
    }
}