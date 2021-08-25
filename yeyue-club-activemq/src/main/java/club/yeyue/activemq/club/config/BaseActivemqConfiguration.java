package club.yeyue.activemq.club.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * 基本消息配置
 *
 * @author fred
 * @date 2021-08-19 15:21
 */
@Configuration
public class BaseActivemqConfiguration {

    public static final String BASE_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME = "baseJmsListenerContainerFactory";

    public static final String BASE_JMS_TEMPLATE_BEAN_NAME = "baseJmsTemplate";

    @Primary
    @Bean(BASE_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public DefaultJmsListenerContainerFactory baseJmsListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 点对点订阅
        factory.setPubSubDomain(false);
        return factory;
    }

    @Primary
    @Bean(BASE_JMS_TEMPLATE_BEAN_NAME)
    public JmsMessagingTemplate baseJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(false);
        return new JmsMessagingTemplate(template);
    }
}
