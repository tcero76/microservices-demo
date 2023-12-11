package cl.sugarfever.postgres.config;

import cl.sugarfever.config.data.service.config.PostGresConfigData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@AllArgsConstructor
public class DataSourceConfig {
    private final PostGresConfigData postGresConfigData;
    @Bean
    @Primary
    @BatchDataSource
    public DataSource postdbDataSource() throws SQLException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        Properties properties = new Properties();
        properties.setProperty("spring.jpa.database-platform", postGresConfigData.getDatabasePlatform());
        properties.setProperty("spring.jpa.show-sql", postGresConfigData.getShowSql());
        dataSource.setDriver(new org.postgresql.Driver());
        dataSource.setUrl(postGresConfigData.getUrl());
        dataSource.setUsername(postGresConfigData.getUsername());
        dataSource.setPassword(postGresConfigData.getPassword());
        dataSource.setConnectionProperties(properties);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
