package club.yeyue.maven.mysql.mybatis.demo.entity;

import club.yeyue.maven.model.MyEnum;
import club.yeyue.maven.mysql.mybatis.entity.AbstractMybatisEntity;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * @author fred
 * @date 2021-05-14 18:14
 */
@Data
// 自动建表
@Table(name = "mybatis_long_entity")
// 指定实体映射
@TableName("mybatis_long_entity")
public class LongMyBatisEntity extends AbstractMybatisEntity {
    private static final long serialVersionUID = -8738356095782794102L;

    @TableId(type = IdType.ASSIGN_ID)
    @Column(isKey = true, isNull = false)
    private Long id;

    // 自动建表
    @Column(length = 32, comment = "姓名")
    private String name;

    @Column
    private Integer age;

    // 映射枚举类型
    @EnumValue
    @Column(type = MySqlTypeConstant.VARCHAR, length = 12)
    private MyEnum myEnum;

}
