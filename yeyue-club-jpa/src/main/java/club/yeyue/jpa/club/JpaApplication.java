package club.yeyue.jpa.club;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author fred
 * @date 2021-07-28 15:12
 */
@Slf4j
/* JPA需要开启的配置 */
@EnableJpaAuditing
@EntityScan(basePackages = "club.yeyue.jpa.club.entity")
@EnableJpaRepositories(basePackages = {"club.yeyue.jpa.club.repository"}
//        , repositoryBaseClass = RepositoryImpl.class
        // 有自己单独实现的repoImpl可以用自己的实现
)
@SpringBootApplication
public class JpaApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(JpaApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 阻塞主线程
//        Thread.currentThread().join();
    }
}
