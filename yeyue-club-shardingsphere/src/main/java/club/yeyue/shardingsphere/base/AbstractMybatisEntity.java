package club.yeyue.shardingsphere.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * mybatis-plus实体超类
 *
 * @author fred
 * @date 2021-05-20 11:02
 */
@Data
public abstract class AbstractMybatisEntity implements Serializable {
    private static final long serialVersionUID = -7060490628211169925L;
    public static final String CREATED = "created";
    public static final String UPDATED = "updated";

    // insert填充字段
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime created;

    // insert/update填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updated;

    // 非原生sql查询时忽略字段
    @TableField(select = false)
    // 指定逻辑删除字段
    @TableLogic(value = "false", delval = "true")
    protected Boolean deleted;

}
