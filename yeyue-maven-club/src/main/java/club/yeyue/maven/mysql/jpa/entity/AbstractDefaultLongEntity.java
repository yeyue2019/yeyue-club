package club.yeyue.maven.mysql.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 子增长Id实体
 *
 * @author fred
 * @date 2021-05-13 16:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractDefaultLongEntity extends AbstractEntity {
    private static final long serialVersionUID = -5960820029534610209L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;
}
