package club.yeyue.maven.socket.tomcat.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录请求
 *
 * @author fred
 * @date 2021-07-06 17:52
 */
@Data
@Accessors(chain = true)
public class LoginRequest implements TomcatSocketMessage, Serializable {
    private static final long serialVersionUID = -8494452158461611375L;

    private String username;
}
