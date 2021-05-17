package club.yeyue.maven.mysql.jpa.demo;

import club.yeyue.maven.mysql.jpa.entity.AbstractSequenceLongEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author fred
 * @date 2021-05-14 17:31
 */
@Data
@Entity
@Table(name = "test_sequence_long_table")
public class SequenceLongEntity extends AbstractSequenceLongEntity {
    private static final long serialVersionUID = -4502883321534234336L;

    @Column(length = 32)
    private String name;

    private Integer age;
}