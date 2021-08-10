import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.v2.TODO;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author fred
 * @date 2021-08-05 17:24
 */
public class BrokerTest {

    public static final WebSocketHttpHeaders DEFAULT_WEB_SOCKET_HEADERS = new WebSocketHttpHeaders();
    public static final StompHeaders DEFAULT_STOMP_HEADERS = new StompHeaders();

    public static WebSocketStompClient getClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient client = new WebSocketStompClient(sockJsClient);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        client.setMessageConverter(converter);
        return client;
    }

    public static JSONObject getMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgId", System.currentTimeMillis());
        jsonObject.put("content", "subscribeTopic1Test发送消息");
        return jsonObject;
    }
    /* 主题订阅模式 */

    @Test
    public void subscribeTopic1Test() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/broker", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            JSONObject jsonObject = new JSONObject();
            StompSession session = future.get();
            session.subscribe("/topic/subscribe", handler);
            session.send("/demo/send_to_all", getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }

    // 测试同时开启两个消费者
    @Test
    public void subscribeTopic2Test() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/broker", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/topic/subscribe", handler);
            session.send("/demo/send_to_all", getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }

    /* 点对点订阅模式 */

    @Test
    public void subscribeUser1est() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/broker?accessToken=user01", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/usr/queue/subscribe", handler);
            session.send("/demo/send_to_one", getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }

    // 不同用户订阅同一主题
    @Test
    public void subscribeUser2est() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/broker?accessToken=user02", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/usr/queue/subscribe", handler);
            session.send("/demo/send_to_one", getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
    }

    // 相同用户订阅同一主题
    // TODO: 2021/8/5 目前仍会出现一个用户多个session都会收到消息的问题
    @Test
    public void subscribeUser3est() throws InterruptedException {
        ReceiveTextStompSessionHandler handler = new ReceiveTextStompSessionHandler();
        ListenableFuture<StompSession> future = getClient().connect("ws://127.0.0.1:8080/broker?accessToken=user01", DEFAULT_WEB_SOCKET_HEADERS, DEFAULT_STOMP_HEADERS, handler);
        try {
            StompSession session = future.get();
            session.subscribe("/usr/queue/subscribe", handler);
            session.send("/demo/send_to_one", getMessage());
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
        System.out.println("接收到信息:" + JSON.toJSONString(payload));
        super.handleFrame(headers, payload);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return JSONObject.class;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("session:" + session.getSessionId() + ",headers:" + JSON.toJSONString(connectedHeaders));
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


