package club.yeyue.maven.mysql.mybatis.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author fred
 * @date 2021-05-14 18:14
 */
@Data
@TableName("test_default_mybatis_entity")
public class DefaultMyBatisEntity implements Serializable {
    private static final long serialVersionUID = -8738356095782794102L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private Integer age;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Boolean deleted;
}
