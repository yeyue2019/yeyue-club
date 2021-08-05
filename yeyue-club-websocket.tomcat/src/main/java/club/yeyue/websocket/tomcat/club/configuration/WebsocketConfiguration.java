package club.yeyue.websocket.tomcat.club.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * tomcat socket 开启自动识别endpoint
 *
 * @author fred
 * @date 2021-07-06 09:53
 */
@Configuration
public class WebsocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
