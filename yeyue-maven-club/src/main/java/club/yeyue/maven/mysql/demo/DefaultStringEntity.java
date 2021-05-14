package club.yeyue.maven.mysql.demo;

import club.yeyue.maven.mysql.jpa.entity.AbstractDefaultStringEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author fred
 * @date 2021-05-14 17:33
 */
@Data
@Entity
@Table(name = "test_default_string_table")
public class DefaultStringEntity extends AbstractDefaultStringEntity {
    private static final long serialVersionUID = -7569772513352462491L;

    @Column(length = 32)
    private String name;

    private Integer age;
}
