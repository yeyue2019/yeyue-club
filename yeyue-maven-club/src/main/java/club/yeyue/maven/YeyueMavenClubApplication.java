package club.yeyue.maven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author fred
 * @date 2021-05-08 15:49
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class YeyueMavenClubApplication implements CommandLineRunner {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new SpringApplicationBuilder(YeyueMavenClubApplication.class)
                .build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("系统已经跑起来了冲冲冲---->>>>>");
    }

}
