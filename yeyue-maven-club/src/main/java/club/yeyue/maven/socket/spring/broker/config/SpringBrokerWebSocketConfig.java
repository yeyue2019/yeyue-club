package club.yeyue.maven.socket.spring.broker.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * stomp 配置
 *
 * @author fred
 * @date 2021-07-13 13:53
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class SpringBrokerWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         * 设置消息代理的前缀
         * 即消息的前缀为 /topic 就会将消息转发给消息代理
         * 客户端 subscribe(/topic/****)
         * 服务端 @SendTo(/topic/****)
         */
        registry.enableSimpleBroker("/topic", "/queue");
        /*
         * 设置由@MessageMapping处理的消息的统一前缀
         * 表示配置一个或多个前缀，通过这些前缀过滤出需要被注解方法处理的消息
         * 客户端 subscribe(/demo/****)
         */
        registry.setApplicationDestinationPrefixes("/demo");
        // 设置用户订阅的前缀 默认 /user
        registry.setUserDestinationPrefix("/usr");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/spring/broker")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .withSockJS();
    }

    public static class CustomHandshakeHandler extends DefaultHandshakeHandler {
        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
                String accessToken = serverRequest.getServletRequest().getParameter("accessToken");
                if (StringUtils.isEmpty(accessToken)) {
                    return super.determineUser(request, wsHandler, attributes);
                }
                return new StompPrincipal(accessToken);
            } else {
                return super.determineUser(request, wsHandler, attributes);
            }
        }
    }

    public static class StompPrincipal implements Principal {
        String name;

        public StompPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }


}
