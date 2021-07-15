package club.yeyue.maven.socket;

import club.yeyue.maven.socket.message.Send2AllMessage;
import club.yeyue.maven.util.JacksonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author fred
 * @date 2021-07-13 14:06
 */
public class SpringBrokerTest {
    public static final WebSocketHttpHeaders DEFAULT_WEB_SOCKET_HEADERS = new WebSocketHttpHeaders();
    public static final StompHeaders DEFAULT_STOMP_HEADERS = new StompHeaders();

    public static WebSocketStompClient getClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient client = new WebSocketStompClient(sockJsClient);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(JacksonUtils.getMapper());
        client.setMessageConverter(converter);
        return client;
    }

    /* 主题订阅模式1 */

    @Test
    public void subscribeTopic1Test() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/spring/broker", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/topic/subscribe", handler);
            session.send("/demo/send_to_all", new Send2AllMessage().setMsgId("10003").setContent("测试下发送消息"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }

    @Test
    public void subscribeTopic2Test() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        DEFAULT_WEB_SOCKET_HEADERS.add("web_socket_headers", "web_socket_headers");
        DEFAULT_STOMP_HEADERS.add("stomp_headers", "stomp_headers");
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/spring/broker", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/topic/subscribe", handler);
            session.send("/demo/send_to_all", new Send2AllMessage().setMsgId("10004").setContent("测试下发送消息"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }

    @Test
    public void subscribeUser1est() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/spring/broker?accessToken=yeyue2021", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/usr/queue/subscribe", handler);
            session.send("/demo/send_to_one", new Send2OneMessage().setMsgId("20003").setContent("测试下发送消息"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }

    @Test
    public void subscribeUser2est() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/spring/broker?accessToken=yeyue2021", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/usr/queue/subscribe", handler);
            session.send("/demo/send_to_one", new Send2OneMessage().setMsgId("20004").setContent("测试下发送消息"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }
}

// 接收到信息的处理方法
class ReceiveTextStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("接收到信息:" + JacksonUtils.toJsonString(payload));
        super.handleFrame(headers, payload);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Send2OneMessage.class;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("session:" + session.getSessionId() + ",headers:" + JacksonUtils.toJsonString(connectedHeaders));
        super.afterConnected(session, connectedHeaders);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.err.println("handleException:" + exception.getMessage());
        super.handleException(session, command, headers, payload, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        // 建议添加断线重连
        System.err.println("handleTransportError:" + exception.getMessage());
        super.handleTransportError(session, exception);
    }
}

@Data
@Accessors(chain = true)
class Send2OneMessage implements Serializable {
    private static final long serialVersionUID = -7428192740426066935L;

    private String username;

    private String msgId;

    private String content;
}
