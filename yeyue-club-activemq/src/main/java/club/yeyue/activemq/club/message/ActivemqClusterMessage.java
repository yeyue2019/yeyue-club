package club.yeyue.activemq.club.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fred
 * @date 2021-08-19 14:46
 */
@Data
@Accessors(chain = true)
public class ActivemqClusterMessage implements Serializable {
    private static final long serialVersionUID = -8565754371068109871L;

    public static final String QUEUE_NAME = "activemq.queue.cluster.01";

    private Integer msgId;

    private String data;
}
