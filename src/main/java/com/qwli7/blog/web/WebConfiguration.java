package com.qwli7.blog.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.qwli7.blog.BlogContextFilter;
import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.exception.BlogExceptionResolver;
import com.qwli7.blog.queue.DataContainer;
import com.qwli7.blog.queue.MemoryDataContainer;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.service.impl.DefaultMarkdown2Html;
import com.qwli7.blog.template.MyAutoDialect;
import com.qwli7.blog.template.SayToAttributeTagProcessor;
import com.qwli7.blog.template.data.DataElementTagProcessor;
import com.qwli7.blog.template.dialect.ExtStandardExpressionDialect;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.servlet.DispatcherType;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
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


    /**
     * 解决跨域请求问题
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .maxAge(5000);
    }

    @Bean("commentNotifyContainer")
    public DataContainer<Comment> dataContainer() {
        return new MemoryDataContainer<>();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        // 允许出现特殊字符和转义符
//        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许出现单引号
//        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段保留，将null值转为""
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
//
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator,
//                                  SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString("");
//
//            }
//        });

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm")));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }


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
            markdown2Html = new DefaultMarkdown2Html.MarkdownConverter(markdownServerUrl, restTemplate());
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
