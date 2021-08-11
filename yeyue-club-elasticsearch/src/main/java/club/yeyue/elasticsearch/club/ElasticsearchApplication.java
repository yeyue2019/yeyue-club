package club.yeyue.elasticsearch.club;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author fred
 * @date 2021-08-10 13:44
 */
@Slf4j
@SpringBootApplication
public class ElasticsearchApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(ElasticsearchApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
