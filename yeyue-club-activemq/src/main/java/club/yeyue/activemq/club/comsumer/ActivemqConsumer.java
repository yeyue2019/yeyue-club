package club.yeyue.activemq.club.comsumer;

import club.yeyue.activemq.club.config.BroadcastAcitvemqConfiguration;
import club.yeyue.activemq.club.config.ClusterActivemqConfiguration;
import club.yeyue.activemq.club.message.ActivemqMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author fred
 * @date 2021-08-19 13:30
 */
@Slf4j
@Component
public class ActivemqConsumer {

    // 普通消息消费
    @JmsListener(destination = ActivemqMessage.QUEUE_NAME)
    public void onMessage(ActivemqMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    // 并发消息消费
    @JmsListener(destination = ActivemqMessage.CONCURRENCY_QUEUE_NAME, concurrency = "2")
    public void onConcurrencyMessage(ActivemqMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    // 集群消息消费
    @JmsListener(destination = ActivemqMessage.CLUSTER_QUEUE_NAME, containerFactory = ClusterActivemqConfiguration.CLUSTERING_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public void onClusterMessage(ActivemqMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    // 广播消息消费
    @JmsListener(destination = ActivemqMessage.BROATCAST_QUEUE_NAME,
            containerFactory = BroadcastAcitvemqConfiguration.BROADCAST_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public void onBroadcastMessage(ActivemqMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
