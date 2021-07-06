package club.yeyue.maven.socket.tomcat.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author fred
 * @date 2021-07-06 18:01
 */
@Data
@Accessors(chain = true)
public class LogoutMessage implements TomcatSocketMessage, Serializable {
    private static final long serialVersionUID = 2907332614373594358L;

    private String username;

    private LocalDateTime loginTime;
}
