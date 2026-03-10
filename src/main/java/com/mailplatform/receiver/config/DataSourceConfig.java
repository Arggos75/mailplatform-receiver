package com.mailplatform.receiver.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        HikariConfig config = new HikariConfig();

        if (databaseUrl != null && !databaseUrl.isBlank()) {
            // Railway fournit : postgresql://user:pass@host:port/dbname
            URI uri = URI.create(databaseUrl);
            String host = uri.getHost();
            int port = uri.getPort() > 0 ? uri.getPort() : 5432;
            String dbName = uri.getPath().substring(1);
            String userInfo = uri.getUserInfo();
            String username = userInfo.split(":")[0];
            String password = userInfo.substring(username.length() + 1);

            config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + dbName);
            config.setUsername(username);
            config.setPassword(password);
        } else {
            // Fallback développement local via variables individuelles
            String host = System.getenv().getOrDefault("PGHOST", "localhost");
            String port = System.getenv().getOrDefault("PGPORT", "5432");
            String db   = System.getenv().getOrDefault("PGDATABASE", "railway");
            config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + db);
            config.setUsername(System.getenv().getOrDefault("PGUSER", "postgres"));
            config.setPassword(System.getenv().getOrDefault("PGPASSWORD", ""));
        }

        return new HikariDataSource(config);
    }
}
