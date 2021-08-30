package club.yeyue.springmvc.club.handler;

import club.yeyue.springmvc.club.model.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author fred
 * @date 2021-08-30 11:17
 */
@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class CommonResponseBodyHandler implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //  指定过滤的返回类型
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof CommonResult) {
            return body;
        } else {
            return CommonResult.success(body);
        }
    }
}
