package club.yeyue.redis.club;

import club.yeyue.redis.base.annotation.EnableRedis;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-08-05 14:49
 */
@EnableRedis
@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(RedisApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
