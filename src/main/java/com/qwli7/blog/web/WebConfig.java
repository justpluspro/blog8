package com.qwli7.blog.web;

import com.qwli7.blog.web.filter.ContextFilter;
import com.qwli7.blog.web.interceptor.AuthInterceptor;
import com.qwli7.blog.web.interceptor.CsrfInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qwli7 
 * @date 2023/2/16 14:53
 * 功能：blog8
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CsrfInterceptor());
        registry.addInterceptor(new AuthInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public FilterRegistrationBean<ContextFilter> contextFilter() {
        FilterRegistrationBean<ContextFilter> contextFilterRegistrationBean = new FilterRegistrationBean<>();
        ContextFilter contextFilter = new ContextFilter();
        contextFilterRegistrationBean.setFilter(contextFilter);
        contextFilterRegistrationBean.setName(ContextFilter.class.getName());
        return contextFilterRegistrationBean;
    }
}
