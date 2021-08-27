package club.yeyue.rabbitmq.club;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-08-25 16:59
 */
@Slf4j
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(RabbitmqApplication.class)
                .build();
        application.run(args);
    }
}
