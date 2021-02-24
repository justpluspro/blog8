package com.qwli7.blog.web;

import com.qwli7.blog.template.MyAutoDialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Set;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/console/**").addResourceLocations("classpath:/console/");
    }


    /* **************************************************************** */
    /*  THYMELEAF-SPECIFIC ARTIFACTS                                    */
    /*  TemplateResolver <- TemplateEngine <- ViewResolver              */
    /* **************************************************************** */
    @Bean
    public ITemplateResolver templateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");

        //Template cache is true by default. Set to false if you want
        //templates to be automatically updated when modified
        templateResolver.setCacheable(true);
        return templateResolver;
    }


    @Bean
    public SpringTemplateEngine templateEngine(ApplicationContext applicationContext) {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true); //是否启用 SpringEL 表达式编译
        templateEngine.setTemplateResolver(templateResolver(applicationContext));
        templateEngine.addDialect(myAutoDialect());
//        templateEngine.setDialect();// 该方法将会导致默认的方言不可用 StandardDialect, 也就是 th:*

        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(ApplicationContext applicationContext) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine(applicationContext));
        return viewResolver;
    }

    @Bean
    public MyAutoDialect myAutoDialect() {
        return new MyAutoDialect();
    }
}
