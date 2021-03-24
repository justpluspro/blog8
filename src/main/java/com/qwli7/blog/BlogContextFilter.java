package com.qwli7.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BlogContextFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final BlogProperties blogProperties;

    public BlogContextFilter(BlogProperties blogProperties) {
        this.blogProperties = blogProperties;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("method<doFilter> enter into ContextFilter");
        try{

//            String ipHeader = blogProperties.getIpHeader();
//            String ip;
//            if(StringUtils.hasText(ipHeader)) {
//                ip = ((HttpServletRequest) servletRequest).getHeader(ipHeader);
//            } else {
//                ip = servletRequest.getRemoteAddr();
//            }
//            BlogContext.setIp(ip);

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            BlogContext.clear();
        }
    }
}
