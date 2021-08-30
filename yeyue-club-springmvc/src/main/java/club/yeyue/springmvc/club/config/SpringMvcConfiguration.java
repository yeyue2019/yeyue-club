package club.yeyue.springmvc.club.config;

import club.yeyue.springmvc.club.interceptor.TraceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author fred
 * @date 2021-08-30 14:38
 */
@Slf4j
@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public TraceInterceptor traceInterceptor() {
        return new TraceInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.traceInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public FilterRegistrationBean<YeyueFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<YeyueFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(this.yeyueFilter());
        filterFilterRegistrationBean.setUrlPatterns(Collections.singleton("/*"));
        return filterFilterRegistrationBean;
    }

    @Bean
    public YeyueFilter yeyueFilter() {
        return new YeyueFilter();
    }

    public static class YeyueFilter extends HttpFilter {
        private static final long serialVersionUID = -6029847772162318949L;

        @Override
        protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            log.info("[YeyueFilter][doFilter]");
            super.doFilter(request, response, chain);
        }
    }
}
