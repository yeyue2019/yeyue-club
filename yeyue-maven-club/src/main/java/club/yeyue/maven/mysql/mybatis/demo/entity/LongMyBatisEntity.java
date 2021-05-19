package club.yeyue.maven.mysql.mybatis.demo.entity;

import club.yeyue.maven.model.MyEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author fred
 * @date 2021-05-14 18:14
 */
@Data
@Table(name = "mybatis_long_entity")
@TableName("mybatis_long_entity")
public class LongMyBatisEntity implements Serializable {
    private static final long serialVersionUID = -8738356095782794102L;

    @TableId(type = IdType.ASSIGN_ID)
    @Column(isKey = true, isNull = false)
    private Long id;

    @Column(length = 32, comment = "姓名")
    private String name;

    @Column
    private Integer age;

    @EnumValue
    @Column(type = MySqlTypeConstant.VARCHAR, length = 12)
    private MyEnum myEnum;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime updated;

    @Column(defaultValue = "false")
    private Boolean deleted;
}
