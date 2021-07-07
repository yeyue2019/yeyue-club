package club.yeyue.maven.socket.spring.handle;

import club.yeyue.maven.socket.message.SocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 消息处理器
 *
 * @author fred
 * @date 2021-07-06 23:27
 */
public interface SpringSocketHandler<T extends SocketMessage> {

    void execute(WebSocketSession session, T message);

    void execute(WebSocketSession session, String message);

    @SuppressWarnings("rawtypes")
    void register(Map<String, Class<? extends SpringSocketHandler>> registerMap);
}
