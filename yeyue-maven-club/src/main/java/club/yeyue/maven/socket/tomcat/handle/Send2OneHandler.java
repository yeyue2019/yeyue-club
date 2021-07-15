package club.yeyue.maven.socket.tomcat.handle;

import club.yeyue.maven.socket.message.ChatRequest;
import club.yeyue.maven.socket.message.ChatResponse;
import club.yeyue.maven.socket.message.Send2OneMessage;
import club.yeyue.maven.socket.tomcat.util.TomcatSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;

/**
 * 一对一消息处理器
 *
 * @author fred
 * @date 2021-07-07 00:22
 */
@Component("tomcatSend2OneHandler")
public class Send2OneHandler implements TomcatSocketHandler<Send2OneMessage> {
    @Override
    public void execute(Session session, Send2OneMessage message) {
        // 响应
        ChatResponse sendResponse = new ChatResponse().setMsgId(message.getMsgId()).setCode(1);
        TomcatSocketUtils.send(session, "CHAT_RES", sendResponse);
        // 转发
        ChatRequest sendToUserRequest = new ChatRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
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