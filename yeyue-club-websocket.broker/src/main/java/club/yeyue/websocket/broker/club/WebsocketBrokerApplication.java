package club.yeyue.websocket.broker.club;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author fred
 * @date 2021-08-05 17:18
 */
@EnableScheduling
@SpringBootApplication
public class WebsocketBrokerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(WebsocketBrokerApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
