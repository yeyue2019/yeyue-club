package club.yeyue.druid.club.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author fred
 * @date 2021-08-01 23:00
 */
@Configuration
public class DruidConfiguration {

    @Primary // 主数据源
    @Bean(name = "oneDataSource", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DruidDataSource oneDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "twoDataSource", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.two")
    public DruidDataSource twoDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
