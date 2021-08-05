package club.yeyue.shardingsphere.club;

import club.yeyue.mybatis.base.annotation.EnableMybatisPlus;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-07-22 14:51
 */
@Slf4j
@EnableMybatisPlus
@MapperScan("club.yeyue.shardingsphere.club.mapper")
@SpringBootApplication
public class ShardingSphereApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(ShardingSphereApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
