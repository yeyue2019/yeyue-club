package club.yeyue.maven.socket.spring.handle;

import club.yeyue.maven.socket.message.ChatRequest;
import club.yeyue.maven.socket.message.ChatResponse;
import club.yeyue.maven.socket.message.Send2OneMessage;
import club.yeyue.maven.socket.spring.util.SpringSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 一对一消息处理器
 *
 * @author fred
 * @date 2021-07-07 00:22
 */
@Component("springSend2OneHandler")
public class Send2OneHandler implements SpringSocketHandler<Send2OneMessage> {
    @Override
    public void execute(WebSocketSession session, Send2OneMessage message) {
        // 响应
        ChatResponse sendResponse = new ChatResponse().setMsgId(message.getMsgId()).setCode(1);
        SpringSocketUtils.send(session, "CHAT_RES", sendResponse);
        // 转发
        ChatRequest sendToUserRequest = new ChatRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        SpringSocketUtils.send(message.getUsername(), "SEND_ONE_MSG", sendToUserRequest);
    }

    @Override
    public void execute(WebSocketSession session, String message) {
        execute(session, JacksonUtils.toObject(message, Send2OneMessage.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends SpringSocketHandler>> registerMap) {
        registerMap.put("SEND_ONE_MSG", Send2OneHandler.class);
    }
}
