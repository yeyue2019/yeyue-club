package club.yeyue.springmvc.club;

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
 * @date 2021-08-27 16:58
 */
@Slf4j
@SpringBootApplication
@EnableMybatisPlus
// 添加自动建表需要扫描的mapper
@ComponentScan(basePackages = {"club.yeyue.springmvc.club", "com.gitee.sunchenbin.mybatis.actable.manager.*"})
// a.c.table自动建表需要扫描的类
@MapperScan(basePackages = {"club.yeyue.springmvc.club.mapper", "com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class SpringmvcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(SpringmvcApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
