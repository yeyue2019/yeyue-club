package club.yeyue.maven.socket.spring.handle;

import club.yeyue.maven.socket.message.ChatRequest;
import club.yeyue.maven.socket.message.ChatResponse;
import club.yeyue.maven.socket.message.Send2AllMessage;
import club.yeyue.maven.socket.spring.util.SpringSocketUtils;
import club.yeyue.maven.util.JacksonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 群发消息处理器
 *
 * @author fred
 * @date 2021-07-07 00:27
 */
@Component("springSend2AllHandler")
public class Send2AllHandler implements SpringSocketHandler<Send2AllMessage> {

    @Override
    public void execute(WebSocketSession session, Send2AllMessage message) {
        // 响应
        ChatResponse sendResponse = new ChatResponse().setMsgId(message.getMsgId()).setCode(1);
        SpringSocketUtils.send(session, "CHAT_RES", sendResponse);
        // 转发
        ChatRequest sendToUserRequest = new ChatRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        SpringSocketUtils.broadcast("SEND_ALL_MSG", sendToUserRequest);
    }

    @Override
    public void execute(WebSocketSession session, String message) {
        execute(session, JacksonUtils.toObject(message, Send2AllMessage.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends SpringSocketHandler>> registerMap) {
        registerMap.put("SEND_ALL_MSG", Send2AllHandler.class);
    }
}
