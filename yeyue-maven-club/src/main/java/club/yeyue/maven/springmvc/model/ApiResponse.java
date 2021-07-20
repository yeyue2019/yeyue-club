package club.yeyue.maven.springmvc.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author fred
 * @date 2021-07-15 17:54
 */
@Data
@Accessors(chain = true)
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 623831665874164054L;
    public static final Integer SUCCESS_CODE = 0;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 错误提示
     */
    private String message;

    public static <T> ApiResponse<T> ok() {
        ApiResponse<T> result = new ApiResponse<>();
        result.code = SUCCESS_CODE;
        result.message = StringUtils.EMPTY;
        return result;
    }

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> result = new ApiResponse<>();
        result.code = SUCCESS_CODE;
        result.data = data;
        result.message = StringUtils.EMPTY;
        return result;
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        ApiResponse<T> result = new ApiResponse<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> ApiResponse<T> error(ApiResponse<?> response) {
        return error(response.getCode(), response.getMessage());
    }

    public boolean ifOk() {
        return SUCCESS_CODE.equals(this.code);
    }

    public boolean ifError() {
        return !ifOk();
    }
}
