package club.yeyue.task.quartz.club.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 *
 * @author fred
 * @date 2021-08-16 17:55
 */
@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "userDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.user.druid")
    public DataSource userDataSource() {
        DataSourceProperties properties = this.userDataSourceProperties();
        return properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }

    @Bean(name = "quartzDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.quartz")
    public DataSourceProperties quartzDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "quartzDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.quartz.druid")
    public DataSource quartzDataSource() {
        DataSourceProperties properties = this.quartzDataSourceProperties();
        return properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }
}
