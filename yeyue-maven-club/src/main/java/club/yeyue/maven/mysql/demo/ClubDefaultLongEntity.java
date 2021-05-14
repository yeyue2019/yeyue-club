package club.yeyue.maven.mysql.demo;

import club.yeyue.maven.mysql.jpa.entity.AbstractDefaultLongEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author fred
 * @date 2021-05-13 16:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "club_long_entity")
public class ClubDefaultLongEntity extends AbstractDefaultLongEntity {
    private static final long serialVersionUID = 1328172284415396471L;

    @Column(length = 32)
    private String clubName;

}
