package club.yeyue.activemq.club.message;

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
public class ActivemqMessage implements Serializable {
    private static final long serialVersionUID = -1185763799935652984L;

    public static final String QUEUE_NAME = "activemq.queue.demo.01";
    public static final String CONCURRENCY_QUEUE_NAME = "activemq.queue.concurrency.01";
    public static final String BROATCAST_QUEUE_NAME = "activemq.queue.broadcast.01";
    public static final String CLUSTER_QUEUE_NAME = "activemq.queue.cluster.01";

    private Integer msgId;

    private String data;
}
