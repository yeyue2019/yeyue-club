package club.yeyue.mysql.jpa.entity;//package club.yeyue.mysql.mybatisplus.entity;

import club.yeyue.mysql.base.jpa.entity.AbstractJpaEntity;
import club.yeyue.mysql.model.Gender;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * jpa实体类
 *
 * @author fred
 * @date 2021-07-20 10:31
 */
@Data
@Entity
@Table(name = "user_jpa_entity")
@Accessors(chain = true)
public class UserJpaEntity extends AbstractJpaEntity {
    private static final long serialVersionUID = -8355293106567697174L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    @Column(precision = 10, scale = 2)
    private BigDecimal grade;

    @Lob
    private String description;
}
