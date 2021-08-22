package club.yeyue.activemq.club.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * 集群消费配置
 * @author fred
 * @date 2021-08-19 14:36
 */
@Configuration
public class ClusterActivemqConfiguration {

    public static final String CLUSTERING_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME = "clusteringJmsListenerContainerFactory";

    public static final String CLUSTERING_JMS_TEMPLATE_BEAN_NAME = "clusteringJmsTemplate";

    @Bean(CLUSTERING_JMS_LISTENER_CONTAINER_FACTORY_BEAN_NAME)
    public DefaultJmsListenerContainerFactory clusteringJmsListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 点对点订阅
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean(CLUSTERING_JMS_TEMPLATE_BEAN_NAME)
    public JmsMessagingTemplate clusteringJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(false);
        return new JmsMessagingTemplate(template);
    }
}
