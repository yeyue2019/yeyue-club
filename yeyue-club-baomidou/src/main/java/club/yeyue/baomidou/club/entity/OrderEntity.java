package club.yeyue.baomidou.club.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fred
 * @date 2021-08-04 15:58
 */
@Data
@Accessors(chain = true)
public class OrderEntity {

    private Integer id;

    private Integer userId;
}
