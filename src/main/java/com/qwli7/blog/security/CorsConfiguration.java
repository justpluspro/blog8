package com.qwli7.blog.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 跨域配置
 * @author liqiwen
 * @since 2.1
 * 当且仅当存在 blog.core.cors-enabled 且值为 true 时才开启全局跨域配置
 */
@Configuration
@ConditionalOnProperty(prefix = "blog.core", name = "cors-enabled", havingValue = "true")
public class CorsConfiguration implements WebMvcConfigurer {


    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 解决跨域请求问题
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("registry cors mapping");
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .maxAge(5000);
    }
}
