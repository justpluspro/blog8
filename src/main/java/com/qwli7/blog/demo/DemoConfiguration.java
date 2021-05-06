package com.qwli7.blog.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Demo 环境配置
 * @author liqiwen
 * @since 2.3
 */
@ConditionalOnProperty(prefix = "blog.core", name = "demo", havingValue = "true")
@Configuration
public class DemoConfiguration {



}
