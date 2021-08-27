package club.yeyue.rabbitmq.club.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author fred
 * @date 2021-08-27 15:41
 */
@Slf4j
@Component
public class RabbitProducerConfirmCallback implements RabbitTemplate.ConfirmCallback {

    public RabbitProducerConfirmCallback(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("[confirm][消息确认成功:{}]", correlationData);
        } else {
            log.error("[confirm][消息确认失败:{},失败原因:{}]", correlationData, cause);
        }
    }
}
