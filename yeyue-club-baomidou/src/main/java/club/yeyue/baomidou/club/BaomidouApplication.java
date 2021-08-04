package club.yeyue.baomidou.club;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author fred
 * @date 2021-08-04 16:02
 */
@EnableAspectJAutoProxy(exposeProxy = true) // 被事务包裹的方法内出现跨数据源的情况,为保证能够切换事务管理器开启的配置
@MapperScan(basePackages = "club.yeyue.baomidou.club.mapper")
@SpringBootApplication
public class BaomidouApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(BaomidouApplication.class)
                .build();
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
