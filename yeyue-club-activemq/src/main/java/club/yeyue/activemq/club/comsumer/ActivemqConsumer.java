package club.yeyue.activemq.club.comsumer;

import club.yeyue.activemq.club.config.BroadcastAcitvemqConfiguration;
import club.yeyue.activemq.club.config.ClusterActivemqConfiguration;
import club.yeyue.activemq.club.message.ActivemqBroadcastMessage;
import club.yeyue.activemq.club.message.ActivemqClusterMessage;
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

    @JmsListener(destination = ActivemqMessage.QUEUE_NAME)
    public void onMessage(ActivemqMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @JmsListener(destination = ActivemqClusterMessage.QUEUE_NAME, containerFactory = ClusterActivemqConfiguration.CLUSTERING_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public void onMessage(ActivemqClusterMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @JmsListener(destination = ActivemqBroadcastMessage.QUEUE_NAME,
            containerFactory = BroadcastAcitvemqConfiguration.BROADCAST_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public void onMessage(ActivemqBroadcastMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
