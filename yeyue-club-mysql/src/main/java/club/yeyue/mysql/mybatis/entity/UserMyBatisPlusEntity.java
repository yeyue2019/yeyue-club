//package club.yeyue.mysql.mybatisplus.entity;
//
//import club.yeyue.maven.model.GenderEnum;
//import club.yeyue.maven.mysql.jpa.entity.AbstractJpaEntity;
//import com.baomidou.mybatisplus.annotation.EnumValue;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
//import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
//import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
//import lombok.Data;
//import lombok.experimental.Accessors;
//
//import java.math.BigDecimal;
//
///**
// * @author fred
// * @date 2021-07-20 10:31
// */
//@Data
//@Table(name = "user_mybatis_plus_entity")
//@TableName("user_mybatis_plus_entity")
//@Accessors(chain = true)
//public class UserMyBatisPlusEntity extends AbstractJpaEntity {
//    private static final long serialVersionUID = -8355293106567697174L;
//
//    @TableId(type = IdType.ASSIGN_ID)
//    @Column(isKey = true, isNull = false, comment = "id")
//    private Long id;
//
//    @Column(length = 32, comment = "姓名", isNull = false)
//    private String username;
//
//    @EnumValue
//    @Column(type = MySqlTypeConstant.VARCHAR, length = 12, comment = "性别")
//    private GenderEnum gender;
//
//    @Column(comment = "年龄")
//    private Integer age;
//
//    @Column(type = MySqlTypeConstant.DECIMAL, length = 10, decimalLength = 2, comment = "等级")
//    private BigDecimal grade;
//
//    @Column(type = MySqlTypeConstant.LONGTEXT, comment = "描述")
//    private String description;
//}
