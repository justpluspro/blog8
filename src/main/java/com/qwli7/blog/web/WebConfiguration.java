package com.qwli7.blog.web;

import com.qwli7.blog.BlogContextFilter;
import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.exception.BlogExceptionResolver;
import com.qwli7.blog.queue.DataContainer;
import com.qwli7.blog.queue.MemoryDataContainer;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.service.impl.DefaultMarkdown2Html;
import com.qwli7.blog.template.*;
import com.qwli7.blog.template.data.DataElementTagProcessor;
import com.qwli7.blog.template.dialect.ExtStandardExpressionDialect;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.annotation.Resource;
import javax.servlet.DispatcherType;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Web 相关配置
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private Markdown2Html markdown2Html;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private TemplateService templateService;


    @Bean
    public TemplateHandlerAdapter templateHandlerAdapter() {
        return new TemplateHandlerAdapter();
    }

    @Bean
    public FilterRegistrationBean<BlogContextFilter> contextFilter(BlogProperties blogProperties) {
        FilterRegistrationBean<BlogContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BlogContextFilter(blogProperties));
        registrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registrationBean.setName(BlogContextFilter.class.getSimpleName());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }


    @Bean
    public BlogExceptionResolver exceptionResolver() {
        return new BlogExceptionResolver();
    }

//
//    @Override
//    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        logger.info("method<extendHandlerExceptionResolvers> resolvers: [{}]", resolvers.size());
//        logger.info("method<extendHandlerExceptionResolvers> resolvers: [{}]", resolvers.toArray().toString());
//        // resolvers 已经有值了
//        // 将自定义的插在第一个位置上
//        resolvers.add(0, exceptionResolver());
//    }
//
//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        logger.info("method<configureHandlerExceptionResolvers> resolvers: [{}]", resolvers.size());
//        logger.info("method<configureHandlerExceptionResolvers> resolvers: [{}]", resolvers.toArray().toString());
//    }


    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }


    @Bean("commentNotifyContainer")
    public DataContainer<Comment> dataContainer() {
        return new MemoryDataContainer<>();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/console/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
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
        templateResolver.setOrder(Ordered.HIGHEST_PRECEDENCE+2);
//        Template cache is true by default. Set to false if you want
//        templates to be automatically updated when modified
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public ITemplateResolver memoryTemplateResolver(TemplateService templateService) {
        MemoryTemplateResolver memoryTemplateResolver = new MemoryTemplateResolver(templateService);
        memoryTemplateResolver.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        return memoryTemplateResolver;
    }


    @Bean
    public SpringTemplateEngine templateEngine(ApplicationContext applicationContext,
                                               BlogProperties blogProperties, TemplateService templateService) {

        final String markdownServerUrl = blogProperties.getMarkdownServerUrl();
        if(StringUtils.isEmpty(markdownServerUrl)) {
            markdown2Html = new DefaultMarkdown2Html.CommonMarkdown2Html();
        } else {
            markdown2Html = new DefaultMarkdown2Html.MarkdownConverter(markdownServerUrl, restTemplate);
        }

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        //是否启用 SpringEL 表达式编译
        templateEngine.setEnableSpringELCompiler(true);
        Set<ITemplateResolver> templateResolvers = new HashSet<>();
        templateResolvers.add(templateResolver(applicationContext));
        templateResolvers.add(memoryTemplateResolver(templateService));
        templateEngine.setTemplateResolvers(templateResolvers);
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
    public ViewResolver viewResolver(ApplicationContext applicationContext, BlogProperties blogProperties, TemplateService templateService) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine(applicationContext, blogProperties, templateService));
        viewResolver.setCharacterEncoding(Charset.defaultCharset().name());
//        viewResolver.setOrder(Ordered.LOWEST_PRECEDENCE-5);
        return viewResolver;
    }

    @Bean
    public MyAutoDialect myAutoDialect() {
        return new MyAutoDialect();
    }
}
