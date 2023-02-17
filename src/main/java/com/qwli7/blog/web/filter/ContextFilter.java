package com.qwli7.blog.web.filter;


import com.qwli7.blog.BlogContext;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.util.IpUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author qwli7 
 * @date 2023/2/16 14:52
 * 功能：blog8
 **/
public class ContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        User user = (User) session.getAttribute("user");
        BlogContext.setAuthorized(user != null);

        String ipAddr = IpUtil.getIpAddr(((HttpServletRequest) servletRequest));
        BlogContext.setIp(ipAddr);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
