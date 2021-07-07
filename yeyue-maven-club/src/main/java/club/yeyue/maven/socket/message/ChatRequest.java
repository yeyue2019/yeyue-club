package club.yeyue.maven.socket.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 消息发送请求
 *
 * @author fred
 * @date 2021-07-06 17:57
 */
@Data
@Accessors(chain = true)
public class ChatRequest implements SocketMessage, Serializable {
    private static final long serialVersionUID = -6035746570964021186L;

    private String msgId;

    private String content;
}
