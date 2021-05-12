package club.yeyue.maven.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fred
 * @date 2021-05-12 16:41
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedisRedissonProperties {

    /**
     * 当前使用的服务端类型
     */
    private ClientType useClient = ClientType.REDISSON;

    /**
     * 客户端类型
     */
    public enum ClientType {

        JEDIS,

        REDISSON,

        LETTUCE
    }
}
