package club.yeyue.maven;

import club.yeyue.maven.mysql.demo.ClubLongEntity;
import club.yeyue.maven.mysql.demo.ClubLongRepo;
import club.yeyue.maven.redis.RedisService;
import club.yeyue.maven.redis.jedis.JedisService;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 启动类
 *
 * @author fred
 * @date 2021-05-08 15:49
 */
@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = "club.yeyue.maven.mysql.demo")
@EnableJpaRepositories(basePackages = "club.yeyue.maven.mysql.demo")
public class YeyueMavenClubApplication implements CommandLineRunner {

    @Resource
    RedisService redisService;

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
//        jedisDemoTest();
//        lettuceDemoTest();
//        redissonDemoTest();
//        jpaSaveTest();
    }

    public void jedisDemoTest() {
        redisService.set("jedisTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("jedisService获取到的值:{}", SpringBeanUtils.getBean(JedisService.class).get("jedisTest"));
    }

    public void lettuceDemoTest() {
        redisService.set("lettuceTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redisService获取到的值:{}", redisService.get("lettuceTest"));
    }

    public void redissonDemoTest() {
        redisService.set("redissonTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redissonService获取到的值:{}", SpringBeanUtils.getBean(RedisService.class).get("redissonTest"));
    }

    public void jpaSaveTest() {
        ClubLongRepo repo = SpringBeanUtils.getBean(ClubLongRepo.class);
        ClubLongEntity entity = new ClubLongEntity();
        entity.setClubName(System.currentTimeMillis() + "");
        entity = repo.save(entity);
        log.info("获取对象:{}", JacksonUtils.toJsonString(entity));
        int count = repo.update(entity.getId(), "夜月");
        log.info("更新数据数量:{}", count);
        List<ClubLongEntity> list = repo.findByName("夜月");
        log.info("获取到的对象集合:{}", JacksonUtils.toJsonString(list));
    }
}
