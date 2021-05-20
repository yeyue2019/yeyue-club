package club.yeyue.maven.mysql.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author fred
 * @date 2021-05-20 11:02
 */
@Data
public abstract class AbstractMybatisEntity implements Serializable {
    private static final long serialVersionUID = -7060490628211169925L;

    @Column
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime created;

    @Column
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updated;

    @Column(defaultValue = "false")
    @TableField(select = false)
    @TableLogic(value = "false", delval = "true")
    protected Boolean deleted;
}
