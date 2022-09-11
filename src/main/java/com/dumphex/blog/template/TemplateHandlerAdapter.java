package com.dumphex.blog.template;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author qwli7
 * @date 2021/2/25 14:49
 * 功能：TemplateHandlerAdapter
 **/
public class TemplateHandlerAdapter implements HandlerAdapter, Ordered {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof String;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response,
                               Object handler) throws Exception {
        final Map<String, Object> pathVariables = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return new ModelAndView(handler.toString()).addAllObjects(pathVariables);
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return -1;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
