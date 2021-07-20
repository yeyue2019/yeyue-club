package club.yeyue.mysql;

import club.yeyue.mysql.base.jpa.repo.JpaRepoImpl;
import club.yeyue.mysql.jpa.entity.UserJpaEntity;
import club.yeyue.mysql.jpa.repo.UserJpaRepo;
import club.yeyue.mysql.model.Gender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fred
 * @date 2021-07-20 13:25
 */
@Slf4j
/* JPA需要的配置 */
@EnableJpaAuditing()
@EntityScan(basePackages = "club.yeyue.mysql.jpa.entity")
@EnableJpaRepositories(basePackages = {"club.yeyue.mysql.jpa.repo"}, repositoryBaseClass = JpaRepoImpl.class)
/* Mybatis-Plus需要的配置 */
//@ComponentScan(value = {"club.yeyue.mysql.*"})
//@MapperScan(basePackages = {"club.yeyue.maven.mysql.mybatis.demo.mapper", "com.gitee.sunchenbin.mybatis.actable.dao.*"})
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
