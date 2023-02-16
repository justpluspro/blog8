package com.qwli7.blog.web.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @author qwli7 
 * @date 2023/2/16 14:52
 * 功能：blog8
 **/
public class ContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
