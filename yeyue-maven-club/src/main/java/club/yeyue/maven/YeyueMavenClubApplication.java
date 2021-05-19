package club.yeyue.maven;

import club.yeyue.maven.mysql.jpa.repo.JpaRepoImpl;
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
 * 启动类
 *
 * @author fred
 * @date 2021-05-08 15:49
 */
@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = "club.yeyue.maven.mysql.jpa.demo.entity")
@EnableJpaRepositories(basePackages = "club.yeyue.maven.mysql.jpa.demo.repo", repositoryBaseClass = JpaRepoImpl.class)
@ComponentScan("com.gitee.sunchenbin.mybatis.actable.manager.*")
@MapperScan(basePackages = {"club.yeyue.maven.mysql.mybatis.demo.mapper", "com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class YeyueMavenClubApplication implements CommandLineRunner {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new SpringApplicationBuilder(YeyueMavenClubApplication.class)
                .build().run(args);
        synchronized (YeyueMavenClubApplication.class) {
            YeyueMavenClubApplication.class.wait();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("系统已经跑起来了冲冲冲---->>>>>");
    }

}
