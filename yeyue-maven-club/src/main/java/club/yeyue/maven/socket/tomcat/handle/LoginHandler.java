package club.yeyue.maven.socket.tomcat.handle;

import club.yeyue.maven.socket.message.LoginMessage;
import club.yeyue.maven.socket.message.LoginRequest;
import club.yeyue.maven.socket.message.LoginResponse;
import club.yeyue.maven.socket.tomcat.util.TomcatSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 登录消息处理器
 *
 * @author fred
 * @date 2021-07-06 23:31
 */
@Component("tomcatLoginHandler")
public class LoginHandler implements TomcatSocketHandler<LoginRequest> {

    @Override
    public void execute(Session session, LoginRequest message) {
        if (StringUtils.isEmpty(message.getUsername())) {
            TomcatSocketUtils.send(session, "LOGIN_RES", new LoginResponse().setCode(0).setMessage("认证 accessToken 未传入"));
            return;
        }
        // 默认登录成功
        TomcatSocketUtils.put(session, message.getUsername());
        TomcatSocketUtils.send(session, "LOGIN_RES", new LoginResponse().setUsername(message.getUsername()).setCode(1));
        // 通知所有人
        TomcatSocketUtils.broadcast("LOGIN_MSG", new LoginMessage().setUsername(message.getUsername()).setLoginTime(LocalDateTime.now()));
    }

    @Override
    public void execute(Session session, String message) {
        execute(session, JacksonUtils.toObject(message, LoginRequest.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends TomcatSocketHandler>> registerMap) {
        registerMap.put("LOGIN_REQ", LoginHandler.class);
    }
}
