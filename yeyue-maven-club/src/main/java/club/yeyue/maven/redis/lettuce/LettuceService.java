package club.yeyue.maven.redis.lettuce;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author fred
 * @date 2021-05-13 11:20
 */
public class LettuceService {

    private volatile static LettuceConnectionFactory factory;

    private LettuceService() {
    }

    public LettuceService(LettuceConnectionFactory factory) {
        LettuceService.factory = factory;
    }

    public String get(String key) {
        RedisStringCommands commands = factory.getConnection().stringCommands();
        byte[] result = commands.get(key.getBytes(StandardCharsets.UTF_8));
        if (Objects.isNull(result)) {
            return null;
        }
        return new String(result, StandardCharsets.UTF_8);
    }
}
