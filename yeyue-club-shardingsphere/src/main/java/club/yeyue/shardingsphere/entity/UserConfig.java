package club.yeyue.shardingsphere.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fred
 * @date 2021-07-22 16:59
 */
@Data
@TableName("user_config")
@Accessors(chain = true)
public class UserConfig implements Serializable {
    private static final long serialVersionUID = -7646387659518812559L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String rule;
}
