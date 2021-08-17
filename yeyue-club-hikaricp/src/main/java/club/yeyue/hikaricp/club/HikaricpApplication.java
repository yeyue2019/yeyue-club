package club.yeyue.hikaricp.club;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author fred
 * @date 2021-08-17 14:04
 */
@Slf4j
@SpringBootApplication
public class HikaricpApplication implements CommandLineRunner {

    @Resource(name = "oneDataSource")
    DataSource oneDataSource;
    @Resource(name = "twoDataSource")
    DataSource twoDataSource;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(HikaricpApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        String sql = "show tables";
        try (Connection connection = oneDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                log.info("执行结果:{}", resultSet.getString(1));
            }
        }
        try (Connection connection = twoDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                log.info("执行结果:{}", resultSet.getString(1));
            }
        }
    }
}
