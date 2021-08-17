package club.yeyue.hikaricp.club.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author fred
 * @date 2021-08-17 14:05
 */
@Configuration
public class HikaricpConfiguration {

    @Primary
    @Bean(name = "oneDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSourceProperties oneDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "oneDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.one.hikari")
    public DataSource oneDataSource() {
        DataSourceProperties properties = this.oneDataSourceProperties();
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "twoDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.two")
    public DataSourceProperties twoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "twoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.two.hikari")
    public DataSource twoDataSource() {
        DataSourceProperties properties = this.twoDataSourceProperties();
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
