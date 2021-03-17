package com.qwli7.blog.file;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletContext;
import java.util.Collections;

/**
 * @author qwli7 
 * @date 2021/3/17 15:24
 * 功能：blog
 **/
@Configuration
public class FileConfiguration {


    private static final String DEFAULT_MVC_STATIC_PATH_PATTERN = "/**";


    public FileConfiguration(WebMvcProperties mvcProperties) {
        String staticPathPattern = mvcProperties.getStaticPathPattern();
        if(DEFAULT_MVC_STATIC_PATH_PATTERN.equals(staticPathPattern)) {
           throw new RuntimeException("文件服务已配置，该文件配置不能为 /**");
        }
    }



    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(PathMatcher pathMatcher,
                                                     FileService fileService,
                                                     ResourceProperties resourceProperties,
                                                     ApplicationContext applicationContext,
                                                     UrlPathHelper urlPathHelper) throws Exception {
//        FrameworkServlet
        FileResourceResolver fileResourceResolver = new FileResourceResolver(fileService);
        FileResourceHttpRequestHandler requestHandler = new FileResourceHttpRequestHandler(fileResourceResolver, resourceProperties);
        requestHandler.setApplicationContext(applicationContext);
        if(applicationContext != null) {
            ServletContext servletContext = ((WebApplicationContext) applicationContext).getServletContext();
            if(servletContext != null) {
                requestHandler.setServletContext(servletContext);
            }
        }
        requestHandler.setUrlPathHelper(urlPathHelper);
        requestHandler.afterPropertiesSet();

        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping(Collections.singletonMap(DEFAULT_MVC_STATIC_PATH_PATTERN, requestHandler));

        simpleUrlHandlerMapping.setPathMatcher(pathMatcher);
        simpleUrlHandlerMapping.setUrlPathHelper(urlPathHelper);
//        simpleUrlHandlerMapping.setAlwaysUseFullPath(false);
//        simpleUrlHandlerMapping.setOrder(Ordered.LOWEST_PRECEDENCE);

        return simpleUrlHandlerMapping;
    }

}
