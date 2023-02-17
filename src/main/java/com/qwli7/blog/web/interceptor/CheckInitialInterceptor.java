package com.qwli7.blog.web.interceptor;

import com.qwli7.blog.entity.GlobalConfig;
import com.qwli7.blog.service.ConfigService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qwli7
 * @date 2023/2/17 17:24
 * 功能：检查是否进行过初始化
 **/
public class CheckInitialInterceptor implements HandlerInterceptor {


    private final ConfigService configService;

    public CheckInitialInterceptor(ConfigService configService) {
        this.configService = configService;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if(requestURI.equals("/install")) {
            return true;
        }
        GlobalConfig globalConfig = configService.loadConfig();
        if(globalConfig == null || !globalConfig.getInitial()) {
            //未初始化
            response.sendRedirect("/install");
            return false;
        }
        return true;
    }
}
