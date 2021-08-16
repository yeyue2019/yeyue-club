package club.yeyue.task.spring.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-08-16 15:02
 */
@SpringBootApplication
public class TaskSpringApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(TaskSpringApplication.class)
                .build();
        application.run(args);
    }
}
