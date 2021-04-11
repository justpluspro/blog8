package com.qwli7.blog.template;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qwli7
 * @date 2021/2/25 14:19
 * 功能：blog
 **/
@Component
@Order(-1)
public class TemplateHandlerMapping implements HandlerMapping {

    private final TemplateService templateService;

    private Map<String, String> urlMap = new ConcurrentHashMap<>();


    public TemplateHandlerMapping(TemplateService templateService) {
        this.templateService = templateService;
    }


    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        String requestURI = request.getRequestURI();
//        if(urlMap.containsKey(requestURI)) {
//            templateService.
//        }
        return null;
    }
}
