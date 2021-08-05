package club.yeyue.websocket.spring.club.handler;

import club.yeyue.websocket.spring.club.core.SocketCore;
import club.yeyue.websocket.spring.club.message.LoginMessage;
import club.yeyue.websocket.spring.club.message.LoginRequest;
import club.yeyue.websocket.spring.club.message.LoginResponse;
import com.alibaba.fastjson.JSON;
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
@Component
public class LoginHandler implements SocketHandler<LoginRequest> {

    @Override
    public void execute(WebSocketSession session, LoginRequest message) {
        if (StringUtils.isEmpty(message.getUsername())) {
            SocketCore.send(session, "LOGIN_RES", new LoginResponse().setCode(0).setMessage("认证 accessToken 未传入"));
            return;
        }
        // 默认登录成功
        SocketCore.put(session, message.getUsername());
        SocketCore.send(session, "LOGIN_RES", new LoginResponse().setUsername(message.getUsername()).setCode(1));
        // 通知所有人
        SocketCore.broadcast("LOGIN_MSG", new LoginMessage().setUsername(message.getUsername()).setLoginTime(LocalDateTime.now()));
    }

    @Override
    public void execute(WebSocketSession session, String message) {
        execute(session, JSON.parseObject(message, LoginRequest.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends SocketHandler>> registerMap) {
        registerMap.put("LOGIN_REQ", LoginHandler.class);
    }
}
