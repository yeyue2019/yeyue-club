package club.yeyue.rabbitmq.club.consumer;

import club.yeyue.rabbitmq.club.message.RabbitmqMessage;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 该配置方式不建议在生产环境使用,不建议在消费者端声明队列和交换机
 *
 * @author fred
 * @date 2021-08-25 16:49
 */
@Slf4j
@Component
public class BaseRabbitmqConsumer {

    public static final String DIRECT_EXCHANGE = "direct.exchange.01";
    public static final String DIRECT_QUEUE = "direct.queue.01";
    public static final String DIRECT_ROUTING_KEY = "direct.route.01";

    public static final String DEAD_EXCHANGE = "dead.exchange.01";
    public static final String DEAD_QUEUE = "dead.queue.01";
    public static final String DEAD_ROUTING_KEY = "dead.route.01";

    public static final String TOPIC_EXCHANGE = "topic.exchange.01";
    public static final String TOPIC_QUEUE = "topic.queue.01";
    public static final String TOPIC_ROUTING_KEY = "topic.route.*";

    public static final String FANOUT_EXCHANGE = "fanout.exchange.01";
    public static final String FANOUT_QUEUE_1 = "fanout.queue.01";
    public static final String FANOUT_QUEUE_2 = "fanout.queue.02";

    public static final String HEADER_EXCHANGE = "header.exchange.01";
    public static final String HEADER_QUEUE = "header.queue.01";

    public static final String DIRECT_QUEUE_2 = "direct.queue.02";
    public static final String DIRECT_ROUTING_KEY_2 = "direct.route.02";

    public static final String DELAY_EXCHANGE = "delay.exchange.01";
    public static final String DELAY_QUEUE = "delay.queue.01";
    public static final String DELAY_ROUTING_KEY = "deley.route.01";

    public static final String DIRECT_QUEUE_3 = "direct.queue.03";
    public static final String DIRECT_ROUTING_KEY_3 = "direct.route.03";

    public static final String DIRECT_QUEUE_4 = "direct.queue.04";
    public static final String DIRECT_ROUTING_KEY_4 = "direct.route.04";

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = DEAD_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = DEAD_EXCHANGE, type = ExchangeTypes.DIRECT, durable = "true", autoDelete = "false"), key = {DEAD_ROUTING_KEY})})
    public void onDeadMessage(RabbitmqMessage message) {
        log.info("[onDeadMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = DIRECT_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT, durable = "true", autoDelete = "false"), key = DIRECT_ROUTING_KEY)})
    public void onDirectMessage(RabbitmqMessage message) {
        log.info("[onDirectMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = TOPIC_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC, durable = "true", autoDelete = "false"), key = TOPIC_ROUTING_KEY)})
    public void onTopicMessage(RabbitmqMessage message) {
        log.info("[onTopicMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = FANOUT_QUEUE_1, durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT, durable = "true", autoDelete = "false"))})
    public void onFanoutMessage1(RabbitmqMessage message) {
        log.info("[onFanoutMessage1][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = FANOUT_QUEUE_2, durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT, durable = "true", autoDelete = "false"))})
    public void onFanoutMessage2(RabbitmqMessage message) {
        log.info("[onFanoutMessage2][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = HEADER_QUEUE, durable = "true", exclusive = "false", autoDelete = "false", arguments = {@Argument(name = "x-match", value = "all"), @Argument(name = "yeyue", value = "yes")}), exchange = @Exchange(name = HEADER_EXCHANGE, type = ExchangeTypes.HEADERS, durable = "true", autoDelete = "false"))})
    public void onHeaderMessage(RabbitmqMessage message) {
        log.info("[onHeaderMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = DIRECT_QUEUE_2, durable = "true", exclusive = "false", autoDelete = "false", arguments = {@Argument(name = "x-dead-letter-exchange", value = DEAD_EXCHANGE), @Argument(name = "x-dead-letter-routing-key", value = DEAD_ROUTING_KEY)}), exchange = @Exchange(name = DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT, durable = "true", autoDelete = "false"), key = DIRECT_ROUTING_KEY_2)})
    public void onDirectErrorMessage(RabbitmqMessage message) {
        log.info("[onDirectErrorMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
        int s = 2 / 0;
    }

    @RabbitListener(queues = {DELAY_QUEUE})
    public void onDelayMessage(RabbitmqMessage message) {
        log.info("[onDelayMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = DIRECT_QUEUE_3, durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT, durable = "true", autoDelete = "false"), key = DIRECT_ROUTING_KEY_3)})
    public Object onRpcMessage(RabbitmqMessage message) {
        log.info("[onRpcMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
        return Collections.singletonMap("data", "success");
    }

    private static final AtomicInteger integer = new AtomicInteger(0);

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = DIRECT_QUEUE_4, durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT, durable = "true", autoDelete = "false"), key = DIRECT_ROUTING_KEY_4)})
    public void onAckMessage(RabbitmqMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("[onAckMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), JSON.toJSONString(message));
        if (integer.incrementAndGet() * 2 == 0) {
            channel.basicAck(deliveryTag, false);
        }
    }
}
