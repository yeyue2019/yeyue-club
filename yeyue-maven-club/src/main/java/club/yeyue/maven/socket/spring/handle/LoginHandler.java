package club.yeyue.maven.socket.spring.handle;

import club.yeyue.maven.socket.message.LoginMessage;
import club.yeyue.maven.socket.message.LoginRequest;
import club.yeyue.maven.socket.message.LoginResponse;
import club.yeyue.maven.socket.spring.util.SpringSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 登录消息处理器
 *
 * @author fred
 * @date 2021-07-06 23:31
 */
@Component("springLoginHandler")
public class LoginHandler implements SpringSocketHandler<LoginRequest> {

    @Override
    public void execute(WebSocketSession session, LoginRequest message) {
        if (StringUtils.isEmpty(message.getUsername())) {
            SpringSocketUtils.send(session, "LOGIN_RES", new LoginResponse().setCode(0).setMessage("认证 accessToken 未传入"));
            return;
        }
        // 默认登录成功
        SpringSocketUtils.put(session, message.getUsername());
        SpringSocketUtils.send(session, "LOGIN_RES", new LoginResponse().setUsername(message.getUsername()).setCode(1));
        // 通知所有人
        SpringSocketUtils.broadcast("LOGIN_MSG", new LoginMessage().setUsername(message.getUsername()).setLoginTime(LocalDateTime.now()));
    }

    @Override
    public void execute(WebSocketSession session, String message) {
        execute(session, JacksonUtils.toObject(message, LoginRequest.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends SpringSocketHandler>> registerMap) {
        registerMap.put("LOGIN_REQ", LoginHandler.class);
    }
}
