package club.yeyue.maven;

import club.yeyue.maven.mysql.jpa.demo.*;
import club.yeyue.maven.mysql.jpa.repo.JpaRepoImpl;
import club.yeyue.maven.redis.RedisService;
import club.yeyue.maven.redis.jedis.JedisService;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SnowflakeIdUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
@EntityScan(basePackages = "club.yeyue.maven.mysql.jpa.demo")
@EnableJpaRepositories(basePackages = "club.yeyue.maven.mysql.jpa.demo", repositoryBaseClass = JpaRepoImpl.class)
public class YeyueMavenClubApplication implements CommandLineRunner {

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
//        jpaTest();
    }

    public void jedisDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("jedisTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("jedisService获取到的值:{}", SpringBeanUtils.getBean(JedisService.class).get("jedisTest"));
    }

    public void lettuceDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("lettuceTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redisService获取到的值:{}", redisService.get("lettuceTest"));
    }

    public void redissonDemoTest() {
        RedisService redisService = SpringBeanUtils.getBean(RedisService.class);
        redisService.set("redissonTest", System.currentTimeMillis() + "", 1L, TimeUnit.DAYS);
        log.info("redissonService获取到的值:{}", SpringBeanUtils.getBean(RedisService.class).get("redissonTest"));
    }

    public void jpaTest() {
        DefaultLongRepo defaultLongRepo = SpringBeanUtils.getBean(DefaultLongRepo.class);
        DefaultStringRepo defaultStringRepo = SpringBeanUtils.getBean(DefaultStringRepo.class);
        SequenceLongRepo sequenceLongRepo = SpringBeanUtils.getBean(SequenceLongRepo.class);
        DefaultLongEntity longEntity = new DefaultLongEntity();
        longEntity.setAge(10);
        longEntity.setName("杨杰");
        defaultLongRepo.save(longEntity);
        List<DefaultLongEntity> longEntity1 = defaultLongRepo.findByName("杨杰");
        int update = defaultLongRepo.updateName(longEntity.getId(), "夜月");
        log.info("获取到实体:{},更新的数量:{}", JacksonUtils.toJsonStringFormat(longEntity1), update);
        DefaultStringEntity stringEntity = new DefaultStringEntity();
        stringEntity.setAge(20);
        stringEntity.setName("憨憨");
        defaultStringRepo.save(stringEntity);
        SequenceLongEntity sequenceLongEntity = new SequenceLongEntity();
        sequenceLongEntity.setId(SnowflakeIdUtils.generate());
        sequenceLongEntity.setName("菜菜");
        sequenceLongEntity.setAge(59);
        sequenceLongRepo.save(sequenceLongEntity);
    }


}
