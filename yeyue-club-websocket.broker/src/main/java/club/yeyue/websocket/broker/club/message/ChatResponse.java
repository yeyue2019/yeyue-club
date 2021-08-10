package club.yeyue.websocket.broker.club.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 消息发送响应
 *
 * @author fred
 * @date 2021-07-06 17:58
 */
@Data
@Accessors(chain = true)
public class ChatResponse implements SocketMessage, Serializable {
    private static final long serialVersionUID = 9136575300960237776L;

    private String msgId;

    private Integer code;

    private String message;
}
