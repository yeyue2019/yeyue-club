package club.yeyue.maven.socket.tomcat.handle;

import club.yeyue.maven.socket.tomcat.model.TomcatSocketMessage;

import javax.websocket.Session;
import java.util.Map;

/**
 * 消息处理器
 *
 * @author fred
 * @date 2021-07-06 23:27
 */
public interface TomcatSocketHandler<T extends TomcatSocketMessage> {

    void execute(Session session, T message);

    void execute(Session session, String message);

    @SuppressWarnings("rawtypes")
    void register(Map<String, Class<? extends TomcatSocketHandler>> registerMap);
}
