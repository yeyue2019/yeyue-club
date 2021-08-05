package club.yeyue.websocket.spring.club.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 群发消息
 *
 * @author fred
 * @date 2021-07-06 18:00
 */
@Data
@Accessors(chain = true)
public class Send2AllMessage implements SocketMessage, Serializable {
    private static final long serialVersionUID = -2215108595030227329L;

    private String msgId;

    private String content;
}

/*
{"type" :  "SEND_ALL_MSG", "message" : { msgId: "1002", "content": "测试广播消息" } }
 */
