package club.yeyue.websocket.spring.club.handler;

import club.yeyue.websocket.spring.club.message.SocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 消息处理器
 *
 * @author fred
 * @date 2021-07-06 23:27
 */
public interface SocketHandler<T extends SocketMessage> {

    void execute(WebSocketSession session, T message);

    void execute(WebSocketSession session, String message);

    @SuppressWarnings("rawtypes")
    void register(Map<String, Class<? extends SocketHandler>> registerMap);
}
