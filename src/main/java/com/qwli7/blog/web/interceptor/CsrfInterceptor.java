package com.qwli7.blog.web.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author qwli7 
 * @date 2023/2/16 15:51
 * 功能：blog8
 **/

public class CsrfInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            String csrfToken = UUID.randomUUID().toString();
            request.setAttribute("_csrf", csrfToken);
            request.getSession().setAttribute("_csrf", csrfToken);
        }
        return true;
    }
}
