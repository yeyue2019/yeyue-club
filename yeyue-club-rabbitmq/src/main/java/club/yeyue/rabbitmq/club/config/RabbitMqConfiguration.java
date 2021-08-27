package club.yeyue.rabbitmq.club.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static club.yeyue.rabbitmq.club.consumer.BaseRabbitmqConsumer.*;

/**
 * 队列自身的配置
 *
 * @author fred
 * @date 2021-08-27 11:32
 */
@Configuration
public class RabbitMqConfiguration {

    public static class DelayMqConfig {

        @Bean("delayExchange")
        public CustomExchange delayExchange() {
            HashMap<String, Object> args = new HashMap<>();
            args.put("x-delayed-type", "direct");
            return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, args);
        }

        @Bean
        public Queue deleyQueue() {
            return new Queue(DELAY_QUEUE, true, false, false);
        }

        @Bean
        public Binding delayBind() {
            return BindingBuilder.bind(deleyQueue()).to(delayExchange()).with(DELAY_ROUTING_KEY).noargs();
        }
    }
}
