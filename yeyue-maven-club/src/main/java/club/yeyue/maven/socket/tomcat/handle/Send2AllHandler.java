package club.yeyue.maven.socket.tomcat.handle;

import club.yeyue.maven.socket.tomcat.model.ChatRequest;
import club.yeyue.maven.socket.tomcat.model.ChatResponse;
import club.yeyue.maven.socket.tomcat.model.Send2AllMessage;
import club.yeyue.maven.socket.tomcat.util.TomcatSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;

/**
 * @author fred
 * @date 2021-07-07 00:27
 */
@Component
public class Send2AllHandler implements TomcatSocketHandler<Send2AllMessage> {

    @Override
    public void execute(Session session, Send2AllMessage message) {
        // 这里，假装直接成功
        ChatResponse sendResponse = new ChatResponse().setMsgId(message.getMsgId()).setCode(1);
        TomcatSocketUtils.send(session, "CHAT_RES", sendResponse);
        // 创建转发的消息
        ChatRequest sendToUserRequest = new ChatRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        // 广播发送
        TomcatSocketUtils.broadcast("SEND_ALL_MSG", sendToUserRequest);
    }

    @Override
    public void execute(Session session, String message) {
        execute(session, JacksonUtils.toObject(message, Send2AllMessage.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends TomcatSocketHandler>> registerMap) {
        registerMap.put("SEND_ALL_MSG", Send2AllHandler.class);
    }
}
