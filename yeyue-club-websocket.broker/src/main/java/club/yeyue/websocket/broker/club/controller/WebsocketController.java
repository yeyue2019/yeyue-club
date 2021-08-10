package club.yeyue.websocket.broker.club.controller;

import club.yeyue.websocket.broker.club.message.Send2AllMessage;
import club.yeyue.websocket.broker.club.message.Send2OneMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fred
 * @date 2021-07-13 14:03
 */
@Slf4j
@Controller
public class WebsocketController {

    @Resource
    SimpMessagingTemplate template;

    @Resource
    SimpMessageSendingOperations messageSendingOperations;

    public static final AtomicInteger INTEGER = new AtomicInteger(0);
    public static final AtomicLong LONG = new AtomicLong(0L);

    @MessageMapping("/send_to_all")
    @SendTo("/topic/subscribe")
    public Send2OneMessage topicSubscribe(Send2AllMessage message) {
        log.info("[topicSubscribe][Send2AllMessage({})]", JSON.toJSONString(message));
        return new Send2OneMessage().setMsgId("topicSubscribe")
                .setContent("发送主题订阅模式的消息").setUsername("sys_01");
    }

    @MessageMapping("/send_to_one")
    @SendToUser(value = "/queue/subscribe", broadcast = false)
    public Send2OneMessage queueSubscribe(Send2OneMessage message, Principal principal) {
        log.info("[queueSubscribe][Send2OneMessage({})]", JSON.toJSONString(message));
        return new Send2OneMessage().setMsgId("queueSubscribe")
                .setContent("发送用户订阅模式的消息").setUsername("sys_02");
    }

    @Scheduled(fixedRate = 10000)
    public void messagePushAll() {
        template.convertAndSend("/topic/subscribe", new Send2OneMessage().setMsgId("topicSubscribe:" + INTEGER.incrementAndGet())
                .setContent("定时发送主题订阅模式的消息").setUsername("sys_01"));
    }

    @Scheduled(fixedRate = 10000)
    public void messagePushOne() {
        // TODO: 2021/7/15 目前该方法会推送到所有username为指定的session,不能够避免广播
        template.convertAndSendToUser("user01", "/queue/subscribe", new Send2OneMessage().setMsgId("topicSubscribe:" + LONG.incrementAndGet())
                .setContent("定时发送用户订阅模式的消息").setUsername("sys_02"));
    }
}



