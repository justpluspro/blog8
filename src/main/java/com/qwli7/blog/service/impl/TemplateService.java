package com.qwli7.blog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TemplateService implements HandlerMapping {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final ApplicationEventPublisher publisher;

    public TemplateService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }


    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {

        return null;
    }
}
