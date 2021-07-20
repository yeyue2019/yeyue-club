package club.yeyue.maven.springmvc.exception;

/**
 * 封装业务异常
 * 使用RuntimeException:不必强制抛出
 * 使用Exception:需要强制抛出
 *
 * @author fred
 * @date 2021-07-15 18:04
 */
public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -7329685374344881461L;

    /**
     * 错误码
     */
    private final Integer code;

    public ServiceException(ServiceExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
