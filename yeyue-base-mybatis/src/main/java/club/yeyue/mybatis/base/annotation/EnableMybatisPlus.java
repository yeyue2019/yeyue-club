package club.yeyue.mybatis.base.annotation;

import club.yeyue.mybatis.base.configuration.MybatisPlusConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author fred
 * @date 2021-07-30 18:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MybatisPlusConfiguration.class})
public @interface EnableMybatisPlus {
}
