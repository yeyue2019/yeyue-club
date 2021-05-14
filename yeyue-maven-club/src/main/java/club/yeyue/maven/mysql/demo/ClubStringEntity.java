package club.yeyue.maven.mysql.demo;

import club.yeyue.maven.mysql.jpa.entity.AbstractStringEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author fred
 * @date 2021-05-14 09:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "club_string_entity")
public class ClubStringEntity extends AbstractStringEntity {
    private static final long serialVersionUID = -8539673865364925696L;

    @Column(length = 32)
    private String clubName;
}
