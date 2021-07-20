package club.yeyue.maven.mysql.mybatis.demo.entity;

import club.yeyue.maven.model.GenderEnum;
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
 * @date 2021-07-20 09:29
 */
@Data
@Table(name = "mvc_user_demo")
@TableName("mvc_user_demo")
public class MvcUserDemo extends AbstractMybatisEntity {
    private static final long serialVersionUID = -7277229102764585711L;

    @TableId(type = IdType.ASSIGN_ID)
    @Column(isKey = true, isNull = false)
    private Long id;

    @Column(length = 32, comment = "姓名", isNull = false)
    private String username;

    @EnumValue
    @Column(type = MySqlTypeConstant.VARCHAR, length = 12)
    private GenderEnum gender;

    @Column
    private Integer age;

}
