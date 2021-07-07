package club.yeyue.maven.socket.spring.config;

import club.yeyue.maven.socket.spring.SpringDemoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * spring webSocket 开启配置
 *
 * @author fred
 * @date 2021-07-07 13:19
 */
@Configuration
// Spring 开启webSocket
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.springDemoHandler(), "/spring")
                .addInterceptors(this.socketShakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public SpringDemoHandler springDemoHandler() {
        return new SpringDemoHandler();
    }

    @Bean
    public SocketShakeInterceptor socketShakeInterceptor() {
        return new SocketShakeInterceptor();
    }

    /**
     * 自定义 HttpSessionHandshakeInterceptor 拦截器
     * <p>
     * 因为 WebSocketSession 无法获得 ws 地址上的请求参数，所以只好通过该拦截器，获得 accessToken 请求参数，设置到 attributes 中
     */
    public static class SocketShakeInterceptor extends HttpSessionHandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
                attributes.put("accessToken", serverRequest.getServletRequest().getParameter("accessToken"));
            }
            return super.beforeHandshake(request, response, wsHandler, attributes);
        }
    }
}
