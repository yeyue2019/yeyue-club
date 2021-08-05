package club.yeyue.mybatis.club;

import club.yeyue.mybatis.base.annotation.EnableMybatisPlus;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fred
 * @date 2021-08-01 20:23
 */
@Slf4j
@EnableMybatisPlus
// 添加自动建表需要扫描的mapper
@ComponentScan(basePackages = {"club.yeyue.mybatis.club", "com.gitee.sunchenbin.mybatis.actable.manager.*"})
// a.c.table自动建表需要扫描的类
@MapperScan(basePackages = {"club.yeyue.mybatis.club.mapper", "com.gitee.sunchenbin.mybatis.actable.dao.*"})
@SpringBootApplication
public class MybatisApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(MybatisApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
