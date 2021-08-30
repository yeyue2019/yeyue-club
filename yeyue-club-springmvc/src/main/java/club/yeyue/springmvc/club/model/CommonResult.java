package club.yeyue.springmvc.club.model;

import club.yeyue.springmvc.club.exception.ServiceException;
import club.yeyue.springmvc.club.exception.ServiceExceptionEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author fred
 * @date 2021-08-27 17:10
 */
@Data
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = 6317834113544475463L;
    private static final String SUCCUESS_CODE = "0";

    // 错误码
    private String code;

    // 错误信息
    private String message;

    // 业务数据
    private T data;

    private CommonResult() {
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = SUCCUESS_CODE;
        result.data = data;
        return result;
    }

    public static <T> CommonResult<T> success() {
        CommonResult<T> result = new CommonResult<>();
        result.code = SUCCUESS_CODE;
        return result;
    }

    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMessage());
    }

    public static <T> CommonResult<T> error(String code, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> CommonResult<T> error(ServiceException e) {
        return error(e.getCode(), e.getMessage());
    }

    public static <T> CommonResult<T> error(ServiceExceptionEnum e) {
        return error(e.getCode(), e.getMessage());
    }

    public boolean ifSuccess() {
        return StringUtils.equals(SUCCUESS_CODE, this.code);
    }

    public boolean ifError() {
        return !ifSuccess();
    }

}
