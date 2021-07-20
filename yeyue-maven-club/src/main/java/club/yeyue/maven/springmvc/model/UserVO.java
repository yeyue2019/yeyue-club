package club.yeyue.maven.springmvc.model;

import club.yeyue.maven.model.GenderEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户模型
 *
 * @author fred
 * @date 2021-07-15 17:44
 */
@Data
@Accessors(chain = true)
public class UserVO {

    private Long id;

    private String username;

    private LocalDateTime created;

    private LocalDateTime updated;

    private GenderEnum gender;

    private Integer age;
}
