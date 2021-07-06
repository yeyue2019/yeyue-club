package club.yeyue.maven.socket.tomcat.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fred
 * @date 2021-07-06 18:00
 */
@Data
@Accessors(chain = true)
public class Send2AllMessage implements TomcatSocketMessage, Serializable {
    private static final long serialVersionUID = -2215108595030227329L;

    private String msgId;

    private String content;
}
