package club.yeyue.springmvc.club.interceptor;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fred
 * @date 2021-08-30 14:24
 */
public class TraceInterceptor implements HandlerInterceptor {
    public static final String TRACE_ID = "tr";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ThreadContext.put(TRACE_ID, UuidUtil.getTimeBasedUuid().toString());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadContext.clearAll();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
