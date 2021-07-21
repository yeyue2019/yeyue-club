package club.yeyue.mysql;

import club.yeyue.mysql.base.jpa.repo.JpaRepoImpl;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author fred
 * @date 2021-07-20 13:25
 */
@Slf4j
/* JPA需要的配置 */
@EnableJpaAuditing
@EntityScan(basePackages = "club.yeyue.mysql.jpa.entity")
@EnableJpaRepositories(basePackages = {"club.yeyue.mysql.jpa.repo"}, repositoryBaseClass = JpaRepoImpl.class)
/* Mybatis-Plus & 自动建表 需要的配置 */
@ComponentScan(value = {"club.yeyue.mysql.*", "com.gitee.sunchenbin.mybatis.actable.manager.*"})
@MapperScan(basePackages = {"club.yeyue.mysql.mybatis.mapper", "com.gitee.sunchenbin.mybatis.actable.dao.*"})
@SpringBootApplication
public class YeyueMysqlClubApplication implements CommandLineRunner {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new SpringApplicationBuilder(YeyueMysqlClubApplication.class)
                .build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("系统已经跑起来了冲冲冲---->>>>>");
    }
}
