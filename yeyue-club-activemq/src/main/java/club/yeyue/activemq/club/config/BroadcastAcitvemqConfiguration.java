package club.yeyue.activemq.club.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * 广播消费配置
 *
 * @author fred
 * @date 2021-08-19 15:09
 */
@Configuration
public class BroadcastAcitvemqConfiguration {

    public static final String BROADCAST_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME = "broadcastJmsListenerContainerFactory";

    public static final String BROADCAST_JMS_TEMPLATE_BEAN_NAME = "broadcastJmsTemplate";

    @Bean(BROADCAST_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public DefaultJmsListenerContainerFactory broadcastJmsListenerContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean(BROADCAST_JMS_TEMPLATE_BEAN_NAME)
    public JmsMessagingTemplate broadcastJmsTemplate(ConnectionFactory connectionFactory) {
        // 创建 JmsTemplate 对象
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(true);
        // 创建 JmsMessageTemplate
        return new JmsMessagingTemplate(template);
    }
}
