package club.yeyue.maven.mysql.mybatis.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 指定insert / update 填充字段
 *
 * @author fred
 * @date 2021-05-20 11:17
 */
@Component
public class MyBatisOperateTimeHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter("created")) {
            strictInsertFill(metaObject, "created", LocalDateTime.class, LocalDateTime.now());
        }
        if (metaObject.hasSetter("updated")) {
            strictInsertFill(metaObject, "updated", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("updated")) {
            strictUpdateFill(metaObject, "updated", LocalDateTime.class, LocalDateTime.now());
        }
    }
}
