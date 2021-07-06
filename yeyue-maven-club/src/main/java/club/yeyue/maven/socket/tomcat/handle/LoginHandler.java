package club.yeyue.maven.socket.tomcat.handle;

import club.yeyue.maven.socket.tomcat.model.LoginMessage;
import club.yeyue.maven.socket.tomcat.model.LoginRequest;
import club.yeyue.maven.socket.tomcat.model.LoginResponse;
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
@Component
public class LoginHandler implements TomcatSocketHandler<LoginRequest> {

    @Override
    public void execute(Session session, LoginRequest message) {
        // 如果未传递 accessToken
        if (StringUtils.isEmpty(message.getUsername())) {
            TomcatSocketUtils.send(session, "LOGIN_RES", new LoginResponse().setCode(0).setMessage("认证 accessToken 未传入"));
            return;
        }
        // 添加到 WebSocketUtil 中
        TomcatSocketUtils.put(session, message.getUsername());

        // 判断是否认证成功。这里，假装直接成功
        TomcatSocketUtils.send(session, "LOGIN_RES", new LoginResponse().setUsername(message.getUsername()).setCode(1));

        // 通知所有人，某个人加入了。这个是可选逻辑，仅仅是为了演示
        TomcatSocketUtils.broadcast("LOGIN_MSG", new LoginMessage().setUsername(message.getUsername()).setLoginTime(LocalDateTime.now()));
        // 考虑到代码简化，我们先直接使用 accessToken 作为 User
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
