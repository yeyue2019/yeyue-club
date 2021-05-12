package club.yeyue.maven.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author fred
 * @date 2021-05-08 22:50
 */
@Component
public class RedisService {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public void setString(String key, String value, Long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
