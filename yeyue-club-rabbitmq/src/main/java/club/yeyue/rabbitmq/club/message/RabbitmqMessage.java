package club.yeyue.rabbitmq.club.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 消息
 *
 * @author fred
 * @date 2021-08-19 13:21
 */
@Data
@Accessors(chain = true)
public class RabbitmqMessage implements Serializable {
    private static final long serialVersionUID = -1185763799935652984L;

    private Integer msgId;

    private String data;
}
