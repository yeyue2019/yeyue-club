package club.yeyue.maven.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author fred
 * @date 2021-05-13 16:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractLongEntity extends AbstractEntity {
    private static final long serialVersionUID = -5960820029534610209L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;
}
