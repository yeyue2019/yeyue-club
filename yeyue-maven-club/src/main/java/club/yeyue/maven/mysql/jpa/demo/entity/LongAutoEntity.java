package club.yeyue.maven.mysql.jpa.demo.entity;

import club.yeyue.maven.model.GenderEnum;
import club.yeyue.maven.mysql.jpa.entity.AbstractJpaEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 测试自增实体类主键
 * @author fred
 * @date 2021-05-14 17:31
 */
@Data
@Entity
@Table(name = "jpa_long_auto_entity")
public class LongAutoEntity extends AbstractJpaEntity {
    private static final long serialVersionUID = -4502883321534234336L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private GenderEnum myEnum;

}
