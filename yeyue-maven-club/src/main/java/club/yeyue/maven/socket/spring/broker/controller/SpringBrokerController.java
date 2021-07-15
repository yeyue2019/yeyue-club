package club.yeyue.maven.socket.spring.broker.controller;

import club.yeyue.maven.socket.message.Send2AllMessage;
import club.yeyue.maven.socket.message.Send2OneMessage;
import club.yeyue.maven.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-07-13 14:03
 */
@Slf4j
@Controller
public class SpringBrokerController {

    @Resource
    SimpMessagingTemplate template;

    @MessageMapping("/send_to_all")
    @SendTo("/topic/subscribe")
    public Send2OneMessage sendToAll(Send2AllMessage message) {
        log.info("[sendToAll][SendToAllRequest({})]", JacksonUtils.toJsonString(message));
        return new Send2OneMessage().setMsgId("res1002")
                .setContent("测试文本").setUsername("username001");
    }

    @MessageMapping("/send_to_one")
    @SendToUser(value = "/queue/subscribe", broadcast = false)
    public Send2OneMessage sendToOne(Send2OneMessage message) {
        log.info("[sendToOne][SendToOneRequest({})]", JacksonUtils.toJsonString(message));
        return new Send2OneMessage().setMsgId("res1003")
                .setContent("测试文本").setUsername("username003");
    }

    @Scheduled(fixedRate = 10000)
    public void messagePushAll() {
        template.convertAndSend("/topic/subscribe", new Send2OneMessage().setUsername("" + System.currentTimeMillis()).setMsgId("10005").setContent("测试定时推送"));
    }

    @Scheduled(fixedRate = 10000)
    public void messagePushOne() {
        // TODO: 2021/7/15 目前该方法会推送到所有username为指定的session,不能够避免广播
        template.convertAndSendToUser("yeyue2021", "/queue/subscribe", new Send2OneMessage().setUsername("" + System.currentTimeMillis()).setMsgId("20005").setContent("测试定时推送"));
    }
}



