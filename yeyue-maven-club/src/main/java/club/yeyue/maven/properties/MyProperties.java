package club.yeyue.maven.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * springboot自定义配置
 *
 * @author fred
 * @date 2021-07-15 16:38
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "yeyue.club")
public class MyProperties {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
