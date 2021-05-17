package club.yeyue.maven.mysql.mybatis.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author fred
 * @date 2021-05-14 18:14
 */
@Data
public class DefaultMyBatisEntity implements Serializable {
    private static final long serialVersionUID = -8738356095782794102L;

    private Long userId;

    private String name;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Boolean deleted;
}
