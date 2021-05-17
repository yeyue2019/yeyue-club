package club.yeyue.maven.redis;

import club.yeyue.maven.redis.jedis.JedisService;
import club.yeyue.maven.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

/**
 * @author fred
 * @date 2021-05-17 09:50
 */
@Slf4j
@SpringBootTest
@Import(SpringBeanUtils.class)
public class RedisClientTest {

    @Test
    public void jedisDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("jedisTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("jedisService获取到的值:{}", SpringBeanUtils.getBean(JedisService.class).get("jedisTest"));
    }

    @Test
    public void lettuceDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("lettuceTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redisService获取到的值:{}", redisService.get("lettuceTest"));
    }

    @Test
    public void redissonDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("redissonTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redissonService获取到的值:{}", SpringBeanUtils.getBean(RedisService.class).get("redissonTest"));
    }
}
