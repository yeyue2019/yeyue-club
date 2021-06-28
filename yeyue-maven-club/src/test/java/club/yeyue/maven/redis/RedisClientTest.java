package club.yeyue.maven.redis;

import club.yeyue.maven.YeyueMavenClubApplication;
import club.yeyue.maven.redis.jedis.JedisService;
import club.yeyue.maven.redis.lettuce.LettuceService;
import club.yeyue.maven.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

/**
 * @author fred
 * @date 2021-05-17 09:50
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {YeyueMavenClubApplication.class})
@Import(SpringBeanUtils.class)
public class RedisClientTest {

    @Test
    public void redisDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("redisTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redisService获取到的值:{}", redisService.get("redisTest"));
    }

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
        log.info("redisService获取到的值:{}", SpringBeanUtils.getBean(LettuceService.class).get("lettuceTest"));
    }

    @Test
    public void redissonDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("redissonTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redissonService获取到的值:{}", SpringBeanUtils.getBean(RedisService.class).get("redissonTest"));
    }
}
