package com.qwli7.blog.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletContext;
import java.util.Collections;

/**
 * @author qwli7 
 * 2021/3/17 15:24
 * 功能：FileConfiguration
 **/
@Configuration
@Conditional(FileCondition.class)
public class FileConfiguration implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private static final String DEFAULT_MVC_STATIC_PATH_PATTERN = "/**";


    public FileConfiguration(WebMvcProperties mvcProperties) {
        String staticPathPattern = mvcProperties.getStaticPathPattern();
        logger.info("method<FileConfiguration> staticPathPattern: [{}]", staticPathPattern);
        if(DEFAULT_MVC_STATIC_PATH_PATTERN.equals(staticPathPattern)) {
            logger.error("method<FileConfiguration> 文件服务已经配置，该配置不允许为 [/**]");
            throw new RuntimeException("文件服务已配置，该文件配置不能为 /**");
        }
    }


    /**
     * 创建访问静态文件 HandlerMapping
     * @param pathMatcher pathMatcher
     * @param fileService fileService
     * @param resourceProperties resourceProperties
     * @param applicationContext applicationContext
     * @param urlPathHelper urlPathHelper
     * @return SimpleUrlHandlerMapping
     * @throws Exception Exception
     */
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(PathMatcher pathMatcher,
                                                     FileService fileService,
                                                     ResourceProperties resourceProperties,
                                                     ApplicationContext applicationContext,
                                                     UrlPathHelper urlPathHelper) throws Exception {
        // 创建文件资源解析器
        FileResourceResolver fileResourceResolver = new FileResourceResolver(fileService);
        // 创建文件处理器
        FileResourceHttpRequestHandler requestHandler = new FileResourceHttpRequestHandler(fileResourceResolver, resourceProperties);
        requestHandler.setApplicationContext(applicationContext);
        if(applicationContext != null) {
            ServletContext servletContext = ((WebApplicationContext) applicationContext).getServletContext();
            if(servletContext != null) {
                requestHandler.setServletContext(servletContext);
            }
        }
        requestHandler.afterPropertiesSet();


        requestHandler.setUrlPathHelper(urlPathHelper);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping(Collections.singletonMap(DEFAULT_MVC_STATIC_PATH_PATTERN, requestHandler));
        mapping.setOrder(Ordered.LOWEST_PRECEDENCE);
        mapping.setPathMatcher(pathMatcher);
        mapping.setUrlPathHelper(urlPathHelper);
//        simpleUrlHandlerMapping.setAlwaysUseFullPath(false);
//        simpleUrlHandlerMapping.setOrder(Ordered.LOWEST_PRECEDENCE);

        return mapping;
    }

}
