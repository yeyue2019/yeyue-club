package club.yeyue.shardingsphere.base;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fred
 * @date 2021-05-20 14:17
 */
@Configuration
public class MybatisPlusConfiguration {

    /**
     * 启用mybatis-plus分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 拦截添加和保存时间
     */
    @Bean
    @Primary
    public MetaObjectHandler operateObjectHandler() {
        return new OperateObjectHandler();
    }

    /**
     * 开启批量操作
     */
    @Bean
    public MyBatisPlusSqlInjector myBatisPlusSqlInjector() {
        return new MyBatisPlusSqlInjector();
    }

    public static class OperateObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            if (metaObject.hasSetter(AbstractMybatisEntity.CREATED)) {
                strictInsertFill(metaObject, AbstractMybatisEntity.CREATED, LocalDateTime.class, LocalDateTime.now());
            }
            if (metaObject.hasSetter(AbstractMybatisEntity.UPDATED)) {
                strictInsertFill(metaObject, AbstractMybatisEntity.UPDATED, LocalDateTime.class, LocalDateTime.now());
            }
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            if (metaObject.hasSetter(AbstractMybatisEntity.UPDATED)) {
                strictUpdateFill(metaObject, AbstractMybatisEntity.UPDATED, LocalDateTime.class, LocalDateTime.now());
            }
        }
    }

    public static class MyBatisPlusSqlInjector extends DefaultSqlInjector {
        @Override
        public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
            List<AbstractMethod> methods = super.getMethodList(mapperClass);
            // 注入一个批量保存的接口,目前只适用于mysql
            methods.add(new InsertBatchSomeColumn());
            return methods;
        }
    }
}
