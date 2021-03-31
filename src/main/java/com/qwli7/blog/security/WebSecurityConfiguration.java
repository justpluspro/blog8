package com.qwli7.blog.security;

import com.qwli7.blog.service.BlackIpService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;

/**
 * @author qwli7
 * 2021/2/26 13:10
 * 功能：WebSecurityConfiguration
 **/
@Configuration
public class WebSecurityConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<BlackIpFilter> registrationBean(BlackIpService blackIpService) {


        FilterRegistrationBean<BlackIpFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new BlackIpFilter(blackIpService));
        filterRegistrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE+1);

        return filterRegistrationBean;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                     Object handler) throws Exception {

                if(handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;

                    Authenticated authenticated = AnnotationUtils.getAnnotation(handlerMethod.getMethod(), Authenticated.class);
                    if(authenticated == null) {
                        authenticated = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Authenticated.class);
                    }

                    if(authenticated != null) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return false;
                    }
                }
                return true;
            }
        }).addPathPatterns("/**");
    }
}
