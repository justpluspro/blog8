package com.qwli7.blog.template;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qwli7
 * @date 2021/2/25 14:49
 * 功能：blog
 **/
@Component
public class TemplateHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return "template".equals(handler);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return null;
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return -1;
    }
}
