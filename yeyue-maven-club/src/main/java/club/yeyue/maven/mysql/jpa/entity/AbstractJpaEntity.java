package club.yeyue.maven.mysql.jpa.entity;

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
 * 数据库实体对象超类
 *
 * @author fred
 * @date 2021-05-13 16:14
 */
@Data
// 指定为超类
@MappedSuperclass
// 监听insert和update填充时间
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractJpaEntity implements Serializable {
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
