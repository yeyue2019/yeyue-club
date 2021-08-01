package club.yeyue.jpa.club.entity;

import club.yeyue.jpa.base.entity.AbstractEntity;
import club.yeyue.jpa.club.model.Gender;
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
@Table(name = "club_jpa_user")
@Accessors(chain = true)
public class UserEntity extends AbstractEntity {
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
