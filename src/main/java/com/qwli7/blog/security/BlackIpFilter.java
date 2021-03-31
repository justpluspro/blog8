package com.qwli7.blog.security;

import com.qwli7.blog.service.BlackIpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qwli7
 * 2021/2/22 13:14
 * 功能：BlackIpFilter
 **/
public class BlackIpFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final BlackIpService blackIpService;

    public BlackIpFilter(BlackIpService blackIpService) {
        this.blackIpService = blackIpService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        logger.debug("method<doFilter> enter into...");

        String ip = servletRequest.getRemoteHost();
        if(!StringUtils.hasText(ip)) {
            ip = servletRequest.getRemoteAddr();
            if(StringUtils.hasText(ip)) {
                final boolean blackIp = blackIpService.isBlackIp(ip);
                if(blackIp) {
                    ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
