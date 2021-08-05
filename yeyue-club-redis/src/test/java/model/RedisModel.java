package model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author fred
 * @date 2021-08-05 15:31
 */
@Data
@Accessors(chain = true)
public class RedisModel {

    private String name;

    private LocalDateTime time;

    private Long value;

    private BigDecimal grade;

    public static RedisModel getInstance() {
        return new RedisModel().setName(System.currentTimeMillis() + "测试用户").setTime(LocalDateTime.now()).setValue(RandomUtils.nextLong()).setGrade(BigDecimal.valueOf(RandomUtils.nextDouble()));
    }
}
