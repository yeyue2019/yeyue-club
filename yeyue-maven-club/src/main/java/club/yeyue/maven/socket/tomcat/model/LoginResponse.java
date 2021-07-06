package club.yeyue.maven.socket.tomcat.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录响应
 *
 * @author fred
 * @date 2021-07-06 17:55
 */
@Data
@Accessors(chain = true)
public class LoginResponse implements TomcatSocketMessage, Serializable {
    private static final long serialVersionUID = 4846490375004837860L;

    private Integer code;

    private String message;

    private String username;

}
