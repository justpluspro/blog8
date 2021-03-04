package com.qwli7.blog.web;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.service.impl.DefaultMarkdown2Html;
import com.qwli7.blog.template.MyAutoDialect;
import com.qwli7.blog.template.SayToAttributeTagProcessor;
import com.qwli7.blog.template.data.DataElementTagProcessor;
import com.qwli7.blog.template.dialect.ExtStandardExpressionDialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private Markdown2Html markdown2Html;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/console/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }


//    @Bean
//    public PageHelperDialect pageHelperDialect() {
//        return new PageHelperDialect();
//    }


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
    public SpringTemplateEngine templateEngine(ApplicationContext applicationContext, BlogProperties blogProperties) {

        final String markdownServerUrl = blogProperties.getMarkdownServerUrl();
        if(StringUtils.isEmpty(markdownServerUrl)) {
            markdown2Html = new DefaultMarkdown2Html.CommonMarkdown2Html();
        } else {
            markdown2Html = new DefaultMarkdown2Html.MarkdownConverter(markdownServerUrl);
        }

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true); //是否启用 SpringEL 表达式编译
        templateEngine.setTemplateResolver(templateResolver(applicationContext));
        templateEngine.addDialect(myAutoDialect());
//        templateEngine.addDialect(pageHelperDialect());
//        templateEngine.add
//        templateEngine.setDialect();// 该方法将会导致默认的方言不可用 StandardDialect, 也就是 th:*

        templateEngine.setAdditionalDialects(new HashSet<>(Arrays.asList(new IProcessorDialect() {
            @Override
            public String getPrefix() {
                return "data";
            }

            @Override
            public int getDialectProcessorPrecedence() {
                return 1000;
            }

            @Override
            public Set<IProcessor> getProcessors(String prefix) {
                return createProcessor(prefix, applicationContext);
            }

            @Override
            public String getName() {
                return "template dialect";
            }
        }, new ExtStandardExpressionDialect(markdown2Html))));
        return templateEngine;
    }


    private Set<IProcessor> createProcessor(String prefix, ApplicationContext applicationContext) {
        Set<IProcessor> processors = new HashSet<>();
        processors.add(new SayToAttributeTagProcessor(prefix));
        DataElementTagProcessor dataElementTagProcessor = new DataElementTagProcessor(prefix);
        dataElementTagProcessor.registerAllProcessors(applicationContext);
        processors.add(dataElementTagProcessor);
        return processors;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(ApplicationContext applicationContext, BlogProperties blogProperties) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine(applicationContext, blogProperties));
        return viewResolver;
    }

    @Bean
    public MyAutoDialect myAutoDialect() {
        return new MyAutoDialect();
    }
}
