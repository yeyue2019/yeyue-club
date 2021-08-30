package club.yeyue.springmvc.club.handler;

import club.yeyue.springmvc.club.exception.ServiceException;
import club.yeyue.springmvc.club.exception.ServiceExceptionEnum;
import club.yeyue.springmvc.club.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fred
 * @date 2021-08-30 13:38
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    public CommonResult<Void> seviceException(HttpServletRequest request, ServiceException e) {
        log.error("业务异常信息打印:CODE:{}|MESSAGE:{}", e.getCode(), e.getMessage());
        return CommonResult.error(e);
    }

    @ExceptionHandler(value = {Exception.class})
    public CommonResult<Void> exception(HttpServletRequest request, Exception e) {
        log.error("异常信息打印", e);
        return CommonResult.error(ServiceExceptionEnum.SYS_ERROR);
    }
}
