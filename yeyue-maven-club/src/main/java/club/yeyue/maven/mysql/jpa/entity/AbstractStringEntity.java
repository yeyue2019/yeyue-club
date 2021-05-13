package club.yeyue.maven.mysql.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author fred
 * @date 2021-05-13 16:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractStringEntity extends AbstractEntity {
    private static final long serialVersionUID = -7434425715209034000L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, unique = true, nullable = false)
    protected String id;
}
