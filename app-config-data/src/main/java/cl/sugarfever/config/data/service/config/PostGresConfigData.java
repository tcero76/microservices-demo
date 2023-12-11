package cl.sugarfever.config.data.service.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "postgres-config")
public class PostGresConfigData {
    private String url;
    private String username;
    private String password;
    private String databasePlatform;
    private String showSql;
    private String initializationMode;
    private String initializeSchema;
}
