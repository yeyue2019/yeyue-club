package club.yeyue.activemq.club.producer;

import club.yeyue.activemq.club.config.BroadcastAcitvemqConfiguration;
import club.yeyue.activemq.club.config.ClusterActivemqConfiguration;
import club.yeyue.activemq.club.message.ActivemqBroadcastMessage;
import club.yeyue.activemq.club.message.ActivemqClusterMessage;
import club.yeyue.activemq.club.message.ActivemqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fred
 * @date 2021-08-19 13:24
 */
@Slf4j
@Component
public class ActivemqProducer {

    @Resource
    JmsMessagingTemplate template;

    @Resource(name = ClusterActivemqConfiguration.CLUSTERING_JMS_TEMPLATE_BEAN_NAME)
    JmsMessagingTemplate clusteringTemplate;

    @Resource(name = BroadcastAcitvemqConfiguration.BROADCAST_JMS_TEMPLATE_BEAN_NAME)
    JmsMessagingTemplate broadcastTemplate;

    public static final AtomicInteger COUNT = new AtomicInteger(0);

    public void syncSend(String data) {
        ActivemqMessage message = new ActivemqMessage().setMsgId(COUNT.incrementAndGet()).setData(data);
        template.convertAndSend(ActivemqMessage.QUEUE_NAME, message);
    }

    public void delaySend(String data, Long delayMillseconds) {
        assert Objects.nonNull(delayMillseconds) && delayMillseconds > 0L;
        ActivemqMessage message = new ActivemqMessage().setMsgId(COUNT.incrementAndGet()).setData(data);
        Map<String, Object> headers = new HashMap<>(2);
        headers.put(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayMillseconds);
        template.convertAndSend(ActivemqMessage.QUEUE_NAME, message, headers);
    }

    public ListenableFuture<Void> asyncSend(String data) {
        try {
            this.syncSend(data);
            return AsyncResult.forValue(null);
        } catch (Throwable throwable) {
            return AsyncResult.forExecutionException(throwable);
        }
    }

    public void syncCluterSend(String data) {
        ActivemqClusterMessage message = new ActivemqClusterMessage().setMsgId(COUNT.incrementAndGet()).setData(data);
        clusteringTemplate.convertAndSend(ActivemqClusterMessage.QUEUE_NAME, message);
    }

    public void syncBroadcastSend(String data) {
        ActivemqBroadcastMessage message = new ActivemqBroadcastMessage().setMsgId(COUNT.incrementAndGet()).setData(data);
        broadcastTemplate.convertAndSend(ActivemqBroadcastMessage.QUEUE_NAME, message);
    }
}
