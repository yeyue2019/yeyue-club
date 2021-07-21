package club.yeyue.mysql.mybatis.entity;

import club.yeyue.mysql.base.mybatis.entity.AbstractMybatisEntity;
import club.yeyue.mysql.model.Gender;
import com.baomidou.mybatisplus.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author fred
 * @date 2021-07-20 10:31
 */
@Data
@TableName("user_mybatis_entity")
@Accessors(chain = true)
public class UserMybatisEntity extends AbstractMybatisEntity {
    private static final long serialVersionUID = -8355293106567697174L;

    /* A.C.Table 兼容mybatis-plus的注解 ： https://www.yuque.com/sunchenbin/actable/gbfhne */

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(length = 32, comment = "姓名", isNull = false)
    private String username;

    @EnumValue
    @Column(type = MySqlTypeConstant.VARCHAR, length = 12, comment = "性别")
    private Gender gender;

    @Column(comment = "年龄")
    private Integer age;

    @Column(type = MySqlTypeConstant.DECIMAL, length = 10, decimalLength = 2, comment = "等级")
    @TableField(numericScale = "2")
    private BigDecimal grade;

    @Column(type = MySqlTypeConstant.LONGTEXT, comment = "描述")
    private String description;
}
