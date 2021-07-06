package club.yeyue.maven.socket.tomcat.handle;

import club.yeyue.maven.socket.tomcat.model.ChatRequest;
import club.yeyue.maven.socket.tomcat.model.ChatResponse;
import club.yeyue.maven.socket.tomcat.model.Send2OneMessage;
import club.yeyue.maven.socket.tomcat.util.TomcatSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;

/**
 * @author fred
 * @date 2021-07-07 00:22
 */
@Component
public class Send2OneHandler implements TomcatSocketHandler<Send2OneMessage> {
    @Override
    public void execute(Session session, Send2OneMessage message) {
        // 这里，假装直接成功
        ChatResponse sendResponse = new ChatResponse().setMsgId(message.getMsgId()).setCode(1);
        TomcatSocketUtils.send(session, "CHAT_RES", sendResponse);
        // 创建转发的消息
        ChatRequest sendToUserRequest = new ChatRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        // 发送
        TomcatSocketUtils.send(message.getUsername(), "SEND_ONE_MSG", sendToUserRequest);
    }

    @Override
    public void execute(Session session, String message) {
        execute(session, JacksonUtils.toObject(message, Send2OneMessage.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends TomcatSocketHandler>> registerMap) {
        registerMap.put("SEND_ONE_MSG", Send2OneHandler.class);
    }
}
