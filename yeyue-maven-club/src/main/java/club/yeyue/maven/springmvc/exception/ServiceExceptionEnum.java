package club.yeyue.maven.springmvc.exception;

import lombok.Getter;

/**
 * 业务异常枚举
 *
 * @author fred
 * @date 2021-07-15 18:07
 */
@Getter
public enum ServiceExceptionEnum {

    SYS_ERROR(500, "服务异常");

    private final int code;
    private final String message;

    ServiceExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
