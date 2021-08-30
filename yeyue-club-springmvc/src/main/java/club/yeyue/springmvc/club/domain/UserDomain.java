package club.yeyue.springmvc.club.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author fred
 * @date 2021-08-27 17:48
 */
@Data
public class UserDomain implements Serializable {
    private static final long serialVersionUID = -8094957442980350599L;

    private Long id;

    private String username;

    private Gender gender;

    private Integer age;

    private BigDecimal grade;

    private String description;

    private LocalDateTime created;

    private LocalDateTime updated;
}
