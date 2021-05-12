package club.yeyue.maven.redis.jedis;

import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

/**
 * @author fred
 * @date 2021-05-12 14:54
 */
public class JedisService {

    private volatile static JedisConnectionFactory factory;

    public JedisService(JedisConnectionFactory factory) {
        JedisService.factory = factory;
    }

    private JedisService() {
    }

    public String get(String key) {
        // TODO: 2021/5/12 效率问题没有测试
        try (Jedis jedis = ((JedisConnection) factory.getConnection()).getJedis()) {
            return jedis.get(key);
        }
    }
}
