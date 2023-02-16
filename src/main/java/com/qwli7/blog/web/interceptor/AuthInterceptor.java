package com.qwli7.blog.web.interceptor;

import com.qwli7.blog.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author qwli7 
 * @date 2023/2/16 15:51
 * 功能：blog8
 **/

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        if(requestUri.equals("/login")) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user != null) {
                request.getRequestDispatcher("/").forward(request, response);
                return false;
            }
        }

        if(requestUri.startsWith("/console")) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user == null) {
                response.sendRedirect("/login?redirect=" +requestUri);
                return false;
            }
        }

        return true;
    }
}
