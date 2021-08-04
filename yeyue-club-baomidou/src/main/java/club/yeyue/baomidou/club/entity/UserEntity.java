package club.yeyue.baomidou.club.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fred
 * @date 2021-08-04 15:57
 */
@Data
@Accessors(chain = true)
public class UserEntity {

    private Integer id;

    private String username;
}
