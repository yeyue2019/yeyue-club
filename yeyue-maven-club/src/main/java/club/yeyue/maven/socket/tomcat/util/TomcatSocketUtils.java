package club.yeyue.maven.socket.tomcat.util;

import club.yeyue.maven.socket.tomcat.model.TomcatSocketMessage;
import club.yeyue.maven.util.JacksonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fred
 * @date 2021-07-06 23:37
 */
@Slf4j
public class TomcatSocketUtils {
    private TomcatSocketUtils() {
    }

    /**
     * 用户会话映射
     */
    private static final ConcurrentHashMap<String, Session> USER_POOL = new ConcurrentHashMap<>();

    /**
     * 会话用户映射
     */
    private static final ConcurrentHashMap<Session, String> SESSION_POOL = new ConcurrentHashMap<>();

    public static void put(Session session, String username) {
        USER_POOL.put(username, session);
        SESSION_POOL.put(session, username);
    }

    public static String remove(Session session) {
        String username = SESSION_POOL.remove(session);
        if (StringUtils.isNotEmpty(username)) {
            USER_POOL.remove(username);
        }
        return username;
    }

    /**
     * 广播发送全部消息
     */
    public static <T extends TomcatSocketMessage> void broadcast(String type, T message) {
        ObjectNode node = JacksonUtils.createJsonObject();
        node.put("type", type);
        node.putPOJO("message", message);
        String sendMsg = JacksonUtils.toJsonString(node);
        for (Session session : SESSION_POOL.keySet()) {
            sendMessage(session, sendMsg);
        }
    }

    /**
     * 发送消息给单个用户的 Session
     */
    public static <T extends TomcatSocketMessage> void send(Session session, String type, T message) {
        ObjectNode node = JacksonUtils.createJsonObject();
        node.put("type", type);
        node.putPOJO("message", message);
        sendMessage(session, JacksonUtils.toJsonString(node));
    }

    /**
     * 发送消息给指定用户 username
     */
    public static <T extends TomcatSocketMessage> void send(String username, String type, T message) {
        send(USER_POOL.get(username), type, message);
    }


    /**
     * 真正发送消息
     */
    private static void sendMessage(Session session, String messageText) {
        RemoteEndpoint.Basic basic;
        if (session == null || ((basic = session.getBasicRemote()) == null)) {
            return;
        }
        try {
            basic.sendText(messageText);
        } catch (IOException e) {
            log.error("[sendTextMessage]session:[{}]发送消息异常:[{}]", session, messageText, e);
        }
    }
}
