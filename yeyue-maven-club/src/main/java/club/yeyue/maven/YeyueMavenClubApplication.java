package club.yeyue.maven;

import club.yeyue.maven.redis.RedisService;
import club.yeyue.maven.redis.jedis.JedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 启动类
 *
 * @author fred
 * @date 2021-05-08 15:49
 */
@Slf4j
@SpringBootApplication
public class YeyueMavenClubApplication implements CommandLineRunner {

    @Resource
    RedisService redisService;
    @Resource
    JedisService jedisService;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new SpringApplicationBuilder(YeyueMavenClubApplication.class)
                .build().run(args);
        synchronized (YeyueMavenClubApplication.class) {
            YeyueMavenClubApplication.class.wait();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("系统已经跑起来了冲冲冲---->>>>>");
        jedisDemoTest();
    }

    public void jedisDemoTest() {
        redisService.set("jedisTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("jedisService获取到的值:{}", jedisService.get("jedisTest"));
    }
}
