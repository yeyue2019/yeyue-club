package club.yeyue.maven.mysql.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author fred
 * @date 2021-05-13 16:14
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {
    private static final long serialVersionUID = -8226179470506222523L;

    @CreatedDate
    @Column
    protected LocalDateTime created;

    @LastModifiedDate
    @Column
    protected LocalDateTime updated;

    @Column(nullable = false)
    protected Boolean deleted = Boolean.FALSE;
}
