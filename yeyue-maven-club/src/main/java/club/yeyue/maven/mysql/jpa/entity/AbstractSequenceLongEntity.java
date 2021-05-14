package club.yeyue.maven.mysql.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * long流水号实体
 *
 * @author fred
 * @date 2021-05-14 17:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class AbstractSequenceLongEntity extends AbstractEntity {
    private static final long serialVersionUID = -7985303113729421927L;

    @Id
    @Column(nullable = false, unique = true)
    protected Long id;
}
