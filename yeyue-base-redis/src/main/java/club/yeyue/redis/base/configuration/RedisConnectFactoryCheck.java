package club.yeyue.redis.base.configuration;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author fred
 * @date 2021-08-05 15:16
 */
@Configuration
@AutoConfigureBefore(RedissonAutoConfiguration.class)
@Import(RedisAutoConfiguration.class)
public class RedisConnectFactoryCheck {
    // TODO: 2021/5/13 添加该配置的目的是强行让redisAutoConfiguration加载在RedissonAutoConfiguration之前:保证redis的三种连接工厂可以按照配置加载 而不是原先的按照 redisson 高于 lettuce和jedis
    // 关注 spring.redis.client-type配置 不传值为redisson 其余值按照配置加载
}
