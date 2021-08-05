package club.yeyue.websocket.tomcat.club.handler;

import club.yeyue.websocket.tomcat.club.core.SocketCore;
import club.yeyue.websocket.tomcat.club.message.ChatRequest;
import club.yeyue.websocket.tomcat.club.message.ChatResponse;
import club.yeyue.websocket.tomcat.club.message.Send2OneMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;

/**
 * 一对一消息处理器
 *
 * @author fred
 * @date 2021-07-07 00:22
 */
@Component("send2OneHandler")
public class Send2OneHandler implements SocketHandler<Send2OneMessage> {
    @Override
    public void execute(Session session, Send2OneMessage message) {
        // 响应
        ChatResponse sendResponse = new ChatResponse().setMsgId(message.getMsgId()).setCode(1);
        SocketCore.send(session, "CHAT_RES", sendResponse);
        // 转发
        ChatRequest sendToUserRequest = new ChatRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        SocketCore.send(message.getUsername(), "SEND_ONE_MSG", sendToUserRequest);
    }

    @Override
    public void execute(Session session, String message) {
        execute(session, JSON.parseObject(message, Send2OneMessage.class));
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void register(Map<String, Class<? extends SocketHandler>> registerMap) {
        registerMap.put("SEND_ONE_MSG", Send2OneHandler.class);
    }
}
