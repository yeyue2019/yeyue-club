import club.yeyue.redis.club.RedisApplication;
import lombok.extern.slf4j.Slf4j;
import model.RedisModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author fred
 * @date 2021-08-05 15:22
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RedisApplication.class})
public class RedisStringTest {

    @Resource
    RedisTemplate<String, String> redisTemplate;
    @Resource
    RedisTemplate<String, RedisModel> redisTemplate2;

    @BeforeEach
    public void set() {
        redisTemplate.opsForValue().set("redisString", "夜月test001_v", 1, TimeUnit.HOURS);
        redisTemplate2.opsForValue().set("redisObject", RedisModel.getInstance(), 1, TimeUnit.HOURS);
    }

    @Test
    public void jedisStringGetTest() {
        String result = redisTemplate.opsForValue().get("redisString");
        log.info("获取结果:{}", result);
    }

    @Test
    public void jedisObjectGetTest() {
        RedisModel result = redisTemplate2.opsForValue().get("redisObject");
        log.info("获取结果:{}", result);
    }
}
