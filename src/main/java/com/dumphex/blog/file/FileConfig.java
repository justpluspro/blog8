package com.dumphex.blog.file;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
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
//@Conditional(FileCondition.class)
public class FileConfig implements WebMvcConfigurer {

//    public FileConfiguration(WebMvcProperties mvcProperties) {
//        String staticPathPattern = mvcProperties.getStaticPathPattern();
//        if("/**".equals(staticPathPattern)) {
//            throw new RuntimeException("文件服务已配置，该文件配置不能为 /**");
//        }
//    }


    /**
     * 创建访问静态文件 HandlerMapping
     * @param pathMatcher pathMatcher
     * @param fileService fileService
     * @param applicationContext applicationContext
     * @param urlPathHelper urlPathHelper
     * @return SimpleUrlHandlerMapping
     * @throws Exception Exception
     */
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(PathMatcher pathMatcher,
                                                     FileService fileService,
                                                     ApplicationContext applicationContext,
                                                     UrlPathHelper urlPathHelper) throws Exception {
        // 创建文件资源解析器
        LocalFileResolver localFileResolver = new LocalFileResolver(fileService);
        // 创建文件处理器
        FileResourceHttpRequestHandler requestHandler = new FileResourceHttpRequestHandler(localFileResolver);
        requestHandler.setApplicationContext(applicationContext);
        if(applicationContext != null) {
            ServletContext servletContext = ((WebApplicationContext) applicationContext).getServletContext();
            if(servletContext != null) {
                requestHandler.setServletContext(servletContext);
            }
        }
        requestHandler.afterPropertiesSet();

        requestHandler.setUrlPathHelper(urlPathHelper);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping(Collections.singletonMap("/**", requestHandler));
        mapping.setOrder(Ordered.LOWEST_PRECEDENCE);
        mapping.setPathMatcher(pathMatcher);
        mapping.setUrlPathHelper(urlPathHelper);
//        mapping.setAlwaysUseFullPath(false);
//        mapping.setOrder(Ordered.LOWEST_PRECEDENCE);

        return mapping;
    }

}
