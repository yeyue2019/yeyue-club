package club.yeyue.maven.socket.spring;

import club.yeyue.maven.socket.message.LoginRequest;
import club.yeyue.maven.socket.message.LogoutMessage;
import club.yeyue.maven.socket.spring.handle.LoginHandler;
import club.yeyue.maven.socket.spring.handle.SpringSocketHandler;
import club.yeyue.maven.socket.spring.util.SpringSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fred
 * @date 2021-07-07 13:34
 */
@Slf4j
public class SpringDemoHandler extends TextWebSocketHandler implements InitializingBean {

    public static final String PING_MSG = "PING";

    @Resource
    private ApplicationContext context;
    /**
     * 消息类型与 MessageHandler 的映射
     */
    @SuppressWarnings("rawtypes")
    private final Map<String, Class<? extends SpringSocketHandler>> HANDLERS = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws Exception {
        context.getBeansOfType(SpringSocketHandler.class)
                .values()
                .forEach(handler -> handler.register(HANDLERS));
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("建立链接:{}", session.getId());
        String accessToken = (String) session.getAttributes().get("accessToken");
        LoginRequest loginRequest = new LoginRequest().setUsername(accessToken);
        LoginHandler handler = (LoginHandler) SpringBeanUtils.getBean(HANDLERS.get("LOGIN_REQ"));
        handler.execute(session, loginRequest);
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String textMessage = message.getPayload();
        if (StringUtils.equalsIgnoreCase(textMessage, PING_MSG)) {
            return;
        }
        log.info("服务端收到消息:{}:{}", session.getId(), message);
        JsonNode node = JacksonUtils.getJson(textMessage);
        String type = node.get("type").asText();
        String msg = JacksonUtils.toJsonString(node.get("message"));
        SpringSocketHandler handler = SpringBeanUtils.getBean(HANDLERS.get(type));
        handler.execute(session, msg);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("关闭链接:{},关闭原因:{}", session.getId(), status);
        String username = SpringSocketUtils.remove(session);
        LogoutMessage message = new LogoutMessage().setUsername(username).setLoginTime(LocalDateTime.now());
        SpringSocketUtils.broadcast("LOGOUT_MSG", message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("发生错误:{}", session.getId(), exception);
    }
}
