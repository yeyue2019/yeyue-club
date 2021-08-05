package club.yeyue.shardingsphere.club.entity;

import club.yeyue.mybatis.base.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author fred
 * @date 2021-07-20 10:31
 */
@Data
@TableName("users")
@Accessors(chain = true)
public class UserEntity extends AbstractEntity {
    private static final long serialVersionUID = -8355293106567697174L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    @EnumValue
    private Gender gender;

    private Integer age;

    @TableField(numericScale = "2")
    private BigDecimal grade;

    private String description;
}
