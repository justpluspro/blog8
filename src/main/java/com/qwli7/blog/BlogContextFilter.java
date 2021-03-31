package com.qwli7.blog;

import com.qwli7.blog.security.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 上下文 Filter
 * @author liqiwen
 * @since 1.2
 */
public class BlogContextFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final BlogProperties blogProperties;

    public BlogContextFilter(BlogProperties blogProperties) {
        this.blogProperties = blogProperties;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("method<doFilter> enter into ContextFilter");
        try{
            final HttpServletRequest request = (HttpServletRequest) servletRequest;

            String ipHeader = blogProperties.getIpHeader();
//            String ip;
//            if(StringUtils.hasText(ipHeader)) {
//                ip = ((HttpServletRequest) servletRequest).getHeader(ipHeader);
//            } else {
//                ip = servletRequest.getRemoteAddr();
//            }
//            BlogContext.setIp(ip);

            String tokenHeader = blogProperties.getTokenHeader();
            final String token = request.getHeader(tokenHeader);
            if(!StringUtils.isEmpty(token) && TokenUtil.valid(token)) {
                BlogContext.setAuthenticated(true);
            }

            if(!BlogContext.isAuthenticated()) {
                final HttpSession session = request.getSession();
                if(session != null && Boolean.TRUE.equals(session.getAttribute("auth_user"))) {
                    BlogContext.setAuthenticated(true);
                }
            }


            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            BlogContext.clear();
        }
    }
}
