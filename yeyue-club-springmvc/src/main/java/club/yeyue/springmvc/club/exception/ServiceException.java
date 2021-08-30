package club.yeyue.springmvc.club.exception;

/**
 * @author fred
 * @date 2021-08-30 13:30
 */
public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = -8045557940100452601L;

    private final String code;

    public ServiceException(ServiceExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

    public String getCode() {
        return code;
    }
}
