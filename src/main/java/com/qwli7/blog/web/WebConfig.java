package com.qwli7.blog.web;

import com.qwli7.blog.plugin.file.LocalFileResourceResolver;
import com.qwli7.blog.service.ConfigService;
import com.qwli7.blog.web.filter.ContextFilter;
import com.qwli7.blog.web.interceptor.AuthInterceptor;
import com.qwli7.blog.web.interceptor.CheckInitialInterceptor;
import com.qwli7.blog.web.interceptor.CsrfInterceptor;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${blog.file.path}")
    private String blogFilePath;

    private final LocalFileResourceResolver fileResourceResolver;

    private final ConfigService configService;

    public WebConfig(LocalFileResourceResolver fileResourceResolver, ConfigService configService) {
        this.fileResourceResolver = fileResourceResolver;
        this.configService = configService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CheckInitialInterceptor(configService));
        registry.addInterceptor(new CsrfInterceptor());
        registry.addInterceptor(new AuthInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("file:" + blogFilePath)
                .resourceChain(false)
                .addResolver(fileResourceResolver);
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
