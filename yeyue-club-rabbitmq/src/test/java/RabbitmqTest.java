import club.yeyue.rabbitmq.club.RabbitmqApplication;
import club.yeyue.rabbitmq.club.consumer.BaseRabbitmqConsumer;
import club.yeyue.rabbitmq.club.message.RabbitmqMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fred
 * @date 2021-08-25 17:01
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RabbitmqApplication.class})
public class RabbitmqTest {

    @Resource
    @Qualifier("rabbitTemplate")
    RabbitTemplate rabbitTemplate;
    @Resource
    @Qualifier("batchingRabbitTemplate")
    BatchingRabbitTemplate batchingRabbitTemplate;

    private static final AtomicInteger COUNT = new AtomicInteger(0);

    public static RabbitmqMessage getMessage(String message) {
        return new RabbitmqMessage().setMsgId(COUNT.incrementAndGet()).setData(message);
    }

    @Test
    public void directSend() throws InterruptedException {
        rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.DIRECT_EXCHANGE, BaseRabbitmqConsumer.DIRECT_ROUTING_KEY, getMessage("测试发送Direct消息"));
        new CountDownLatch(1).await();
    }

    @Test
    public void directRetrySend() throws InterruptedException {
        rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.DIRECT_EXCHANGE, BaseRabbitmqConsumer.DIRECT_ROUTING_KEY_2, getMessage("测试发送Direct重试消息"));
        new CountDownLatch(1).await();
    }

    @Test
    public void directBatchSend() throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            batchingRabbitTemplate.convertAndSend(BaseRabbitmqConsumer.DIRECT_EXCHANGE, BaseRabbitmqConsumer.DIRECT_ROUTING_KEY, getMessage("测试发送批量Direct消息"));
        }
        new CountDownLatch(1).await();
    }

    @Test
    public void directDelaySend() throws InterruptedException {
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setDelay(10000);
            return message;
        };
        rabbitTemplate.convertAndSend("delay.exchange.01", "deley.route.01", getMessage("测试发送Direct延时消息"), messagePostProcessor);
        new CountDownLatch(1).await();
    }

    @Test
    public void directRpcSend() throws InterruptedException {
        Object response = rabbitTemplate.convertSendAndReceive(BaseRabbitmqConsumer.DIRECT_EXCHANGE, BaseRabbitmqConsumer.DIRECT_ROUTING_KEY_3, getMessage("测试发送DirectRpc消息"));
        System.out.println(JSON.toJSONString(response));
        new CountDownLatch(1).await();
    }


    @Test
    public void topicSend() throws InterruptedException {
        rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.TOPIC_EXCHANGE, "topic.route.01", getMessage("测试发送Topic消息"));
        new CountDownLatch(1).await();
    }

    @Test
    public void fanoutSend() throws InterruptedException {
        rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.FANOUT_EXCHANGE, null, getMessage("测试发送Fanout消息"));
        new CountDownLatch(1).await();
    }

    @Test
    public void headerSend1() throws InterruptedException {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("x-match", "all");
        properties.setHeader("yeyue", "yes");
        rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.HEADER_EXCHANGE, null, new SimpleMessageConverter().toMessage(getMessage("测试发送Header消息"), properties));
        new CountDownLatch(1).await();
    }

    @Test
    public void headerSend2() throws InterruptedException {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("x-match", "all");
        properties.setHeader("yeyue", "no");
        rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.HEADER_EXCHANGE, null, new SimpleMessageConverter().toMessage(getMessage("测试发送Header消息"), properties));
        new CountDownLatch(1).await();
    }

    // TODO: 2021/8/27 启动时携带参数 -Dspring.rabbitmq.listener.simple.acknowledge-mode=manual
    @Test
    public void directAckSend() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.DIRECT_EXCHANGE, BaseRabbitmqConsumer.DIRECT_ROUTING_KEY_4, getMessage("测试发送Direct Ack消息"));
        }
        new CountDownLatch(1).await();
    }

    // TODO: 2021/8/27 启动时携带参数 -Dspring.rabbitmq.publisher-confirm-type=simple
    @Test
    public void directSyncConfirmTest() throws InterruptedException {
        rabbitTemplate.invoke(operations -> {
            operations.convertAndSend(BaseRabbitmqConsumer.DIRECT_EXCHANGE, BaseRabbitmqConsumer.DIRECT_ROUTING_KEY, getMessage("测试发送Direct消息"));
            System.out.println("消息发送完成");
            operations.waitForConfirms(0L);
            System.out.println("等待confirm完成");
            return null;
        }, (deliveryTag, multiple) -> {
            System.out.println("消息已确认");
        }, (deliveryTag, multiple) -> {
            System.out.println("消息确认失败");
        });
        new CountDownLatch(1).await();
    }

    // TODO: 2021/8/27 启动时携带参数 -Dspring.rabbitmq.publisher-confirm-type=correlated
    @Test
    public void directASyncConfirmTest() throws InterruptedException {
        rabbitTemplate.convertAndSend(BaseRabbitmqConsumer.DIRECT_EXCHANGE, BaseRabbitmqConsumer.DIRECT_ROUTING_KEY, getMessage("测试发送Direct消息"));
        new CountDownLatch(1).await();
    }
}
