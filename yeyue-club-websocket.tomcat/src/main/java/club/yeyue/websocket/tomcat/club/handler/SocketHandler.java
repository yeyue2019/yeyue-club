package club.yeyue.websocket.tomcat.club.handler;

import club.yeyue.websocket.tomcat.club.message.SocketMessage;

import javax.websocket.Session;
import java.util.Map;

/**
 * 消息处理器
 *
 * @author fred
 * @date 2021-07-06 23:27
 */
public interface SocketHandler<T extends SocketMessage> {

    void execute(Session session, T message);

    void execute(Session session, String message);

    @SuppressWarnings("rawtypes")
    void register(Map<String, Class<? extends SocketHandler>> registerMap);
}
