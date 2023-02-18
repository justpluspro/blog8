package com.qwli7.blog.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验请求头中的 token
 * @author liqiwen
 */
public class CheckCsrfInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {;
        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/api/")) {
            //后台接口


        }

        return true;
    }
}
