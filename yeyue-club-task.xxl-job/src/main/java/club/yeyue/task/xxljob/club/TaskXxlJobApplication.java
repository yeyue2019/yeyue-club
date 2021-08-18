package club.yeyue.task.xxljob.club;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-08-17 15:09
 */
@Slf4j
@SpringBootApplication
public class TaskXxlJobApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(TaskXxlJobApplication.class)
                .build();
        application.run(args);
    }
}
