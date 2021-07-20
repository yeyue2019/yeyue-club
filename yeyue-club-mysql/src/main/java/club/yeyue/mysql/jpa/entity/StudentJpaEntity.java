package club.yeyue.mysql.jpa.entity;

import club.yeyue.mysql.base.jpa.entity.AbstractJpaEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author fred
 * @date 2021-07-20 16:54
 */
@Data
@Entity
@Table(name = "student_jpa_entity")
@Accessors(chain = true)
public class StudentJpaEntity extends AbstractJpaEntity {
    private static final long serialVersionUID = -8083992542973241824L;

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(length = 32)
    private String username;
}
