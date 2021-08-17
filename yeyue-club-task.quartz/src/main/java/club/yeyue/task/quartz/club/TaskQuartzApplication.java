package club.yeyue.task.quartz.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-08-16 17:39
 */
@SpringBootApplication
public class TaskQuartzApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(TaskQuartzApplication.class)
                .build();
        application.run(args);
    }
}
