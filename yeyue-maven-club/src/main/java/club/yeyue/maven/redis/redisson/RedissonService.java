package club.yeyue.maven.redis.redisson;

import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

/**
 * @author fred
 * @date 2021-05-13 09:27
 */
public class RedissonService {

    private volatile static RedissonClient redissonClient;

    private RedissonService() {
    }

    public RedissonService(RedissonClient redissonClient) {
        RedissonService.redissonClient = redissonClient;
    }

    public String get(String key) {
        return redissonClient.<String>getBucket(key, StringCodec.INSTANCE).get();
    }
}
