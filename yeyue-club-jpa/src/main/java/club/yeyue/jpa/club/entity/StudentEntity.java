package club.yeyue.jpa.club.entity;

import club.yeyue.jpa.base.entity.AbstractEntity;
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
@Table(name = "club_jpa_student")
@Accessors(chain = true)
public class StudentEntity extends AbstractEntity {
    private static final long serialVersionUID = -8083992542973241824L;

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(length = 32)
    private String username;
}
