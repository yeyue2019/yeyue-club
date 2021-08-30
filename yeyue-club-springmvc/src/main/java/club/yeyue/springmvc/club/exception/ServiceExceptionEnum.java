package club.yeyue.springmvc.club.exception;

/**
 * @author fred
 * @date 2021-08-30 13:31
 */
public enum ServiceExceptionEnum {

    SUCCESS("0", "SUCCESS"),

    SYS_ERROR("-1", "系统错误"),
    ;

    private final String code;

    private final String message;

    ServiceExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
