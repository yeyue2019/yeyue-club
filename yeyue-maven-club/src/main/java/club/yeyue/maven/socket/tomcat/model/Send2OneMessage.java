package club.yeyue.maven.socket.tomcat.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 私聊消息
 *
 * @author fred
 * @date 2021-07-06 17:59
 */
@Data
@Accessors(chain = true)
public class Send2OneMessage implements TomcatSocketMessage, Serializable {
    private static final long serialVersionUID = -7428192740426066935L;

    private String username;

    private String msgId;

    private String content;
}
