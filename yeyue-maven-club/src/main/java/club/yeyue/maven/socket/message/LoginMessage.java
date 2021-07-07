package club.yeyue.maven.socket.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录推送消息
 *
 * @author fred
 * @date 2021-07-06 17:56
 */
@Data
@Accessors(chain = true)
public class LoginMessage implements SocketMessage, Serializable {
    private static final long serialVersionUID = -5315067550447238083L;

    private String username;

    private LocalDateTime loginTime;
}
