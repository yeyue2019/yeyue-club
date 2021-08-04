package club.yeyue.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * @author fred
 * @date 2021-07-22 14:51
 */
@Slf4j

@MapperScan("club.yeyue.shardingsphere.mapper")
@SpringBootApplication
public class YeyueShardingSphereClubApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(YeyueShardingSphereClubApplication.class)
                .build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
