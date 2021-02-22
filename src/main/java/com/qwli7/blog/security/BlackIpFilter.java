package com.qwli7.blog.security;

import com.qwli7.blog.service.BlackIpService;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author qwli7
 * @date 2021/2/22 13:14
 * 功能：blog8
 **/
public class BlackIpFilter implements Filter {

    private final BlackIpService blackIpService;

    public BlackIpFilter(BlackIpService blackIpService) {
        this.blackIpService = blackIpService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
