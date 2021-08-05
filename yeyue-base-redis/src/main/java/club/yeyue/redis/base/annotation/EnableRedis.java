package club.yeyue.redis.base.annotation;

import club.yeyue.redis.base.configuration.MyRedisConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author fred
 * @date 2021-08-05 15:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MyRedisConfiguration.class})
public @interface EnableRedis {
}
