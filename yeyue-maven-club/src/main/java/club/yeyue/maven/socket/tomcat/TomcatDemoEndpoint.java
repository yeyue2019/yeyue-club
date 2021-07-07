package club.yeyue.maven.socket.tomcat;

import club.yeyue.maven.socket.message.LoginRequest;
import club.yeyue.maven.socket.message.LogoutMessage;
import club.yeyue.maven.socket.tomcat.handle.LoginHandler;
import club.yeyue.maven.socket.tomcat.handle.TomcatSocketHandler;
import club.yeyue.maven.socket.tomcat.util.TomcatSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * socket管理
 *
 * @author fred
 * @date 2021-07-07 00:30
 */
@Slf4j
@ServerEndpoint(value = "/tomcat")
@Controller
public class TomcatDemoEndpoint implements InitializingBean {

    public static final String PING_MSG = "PING";

    @Resource
    private ApplicationContext context;
    /**
     * 消息类型与 MessageHandler 的映射
     * <p>
     * 注意，这里设置成静态变量。虽然说 WebsocketServerEndpoint 是单例，但是 Spring Boot 还是会为每个 WebSocket 创建一个 WebsocketServerEndpoint Bean 。
     */
    @SuppressWarnings("rawtypes")
    private static final Map<String, Class<? extends TomcatSocketHandler>> HANDLERS = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() {
        context.getBeansOfType(TomcatSocketHandler.class)
                .values()
                .forEach(handler -> handler.register(HANDLERS));
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.info("建立链接:{}", session.getId());
        // <1> 解析 accessToken
        List<String> accessTokenValues = session.getRequestParameterMap().get("accessToken");
        String accessToken = !CollectionUtils.isEmpty(accessTokenValues) ? accessTokenValues.get(0) : null;
        // <2> 创建 AuthRequest 消息类型
        LoginRequest loginRequest = new LoginRequest().setUsername(accessToken);
        // <3> 获得消息处理器
        LoginHandler handler = (LoginHandler) SpringBeanUtils.getBean(HANDLERS.get("LOGIN_REQ"));
        handler.execute(session, loginRequest);
    }

    @OnMessage
    @SuppressWarnings("rawtypes")
    public void onMessage(Session session, String message) {
        if (StringUtils.equalsIgnoreCase(message, PING_MSG)) {
            return;
        }
        log.info("服务端收到消息:{}:{}", session.getId(), message);
        JsonNode node = JacksonUtils.getJson(message);
        String type = node.get("type").asText();
        String msg = JacksonUtils.toJsonString(node.get("message"));
        TomcatSocketHandler handler = SpringBeanUtils.getBean(HANDLERS.get(type));
        handler.execute(session, msg);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info("关闭链接:{},关闭原因:{}", session.getId(), reason);
        String username = TomcatSocketUtils.remove(session);
        LogoutMessage message = new LogoutMessage().setUsername(username).setLoginTime(LocalDateTime.now());
        TomcatSocketUtils.broadcast("LOGOUT_MSG", message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("发生错误:{}", session.getId(), throwable);
    }
}
