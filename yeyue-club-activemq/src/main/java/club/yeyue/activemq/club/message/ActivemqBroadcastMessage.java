package club.yeyue.activemq.club.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fred
 * @date 2021-08-19 15:08
 */
@Data
@Accessors(chain = true)
public class ActivemqBroadcastMessage implements Serializable {
    private static final long serialVersionUID = -222826772533999900L;

    public static final String QUEUE_NAME = "activemq.queue.broadcast.01";

    private Integer msgId;

    private String data;
}
