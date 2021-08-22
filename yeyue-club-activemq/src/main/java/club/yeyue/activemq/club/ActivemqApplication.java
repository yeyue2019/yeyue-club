package club.yeyue.activemq.club;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author fred
 * @date 2021-08-18 17:58
 */
@Slf4j
@EnableAsync
@SpringBootApplication
public class ActivemqApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(ActivemqApplication.class)
                .build();
        application.run(args);
    }
}
