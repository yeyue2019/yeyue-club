package club.yeyue.websocket.tomcat.club;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-08-05 16:20
 */
@SpringBootApplication
public class WebsocketTomcatApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(WebsocketTomcatApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
