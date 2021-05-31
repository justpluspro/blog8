package com.qwli7.blog.template;

import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.service.MomentService;
import com.qwli7.blog.template.data.*;
import com.qwli7.blog.template.dialect.ExtStandardExpressionDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 模板配置类
 * @author liqiwen
 * @since 2.2
 */
@Configuration
public class TemplateConfiguration {

    private final Map<String, AbstractDataProvider<?>> dataProviderMap;

    public TemplateConfiguration(ArticleService articleService, MomentService momentService) {
        dataProviderMap = new HashMap<>();
        final ArticleDataProvider articleDataProvider = new ArticleDataProvider(articleService);
        final MomentDataProvider momentDataProvider = new MomentDataProvider(momentService);
        final ArticlesDataProvider articlesDataProvider = new ArticlesDataProvider(articleService);
        final MomentsDataProvider momentsDataProvider = new MomentsDataProvider(momentService);
        final LatestMomentsDataProvider latestMomentsDataProvider = new LatestMomentsDataProvider(momentService);

        dataProviderMap.put(articleDataProvider.getName(), articleDataProvider);
        dataProviderMap.put(momentDataProvider.getName(), momentDataProvider);
        dataProviderMap.put(articlesDataProvider.getName(), articlesDataProvider);
        dataProviderMap.put(momentsDataProvider.getName(), momentsDataProvider);
        dataProviderMap.put(latestMomentsDataProvider.getName(), latestMomentsDataProvider);
    }

    @Bean
    public TemplateHandlerAdapter templateHandlerAdapter() {
        return new TemplateHandlerAdapter();
    }


    @Bean
    public SpringTemplateEngine templateEngine(Markdown2Html markdown2Html, TemplateService templateService) {

        final ITemplateResolver iTemplateResolver = templateResolver();
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        final ITemplateResolver memoryTemplateResolver = memoryTemplateResolver(templateService);

        Set<ITemplateResolver> templateResolvers = new HashSet<>();
        templateResolvers.add(iTemplateResolver);
        templateResolvers.add(memoryTemplateResolver);

        springTemplateEngine.setTemplateResolvers(templateResolvers);
        springTemplateEngine.addDialect("expression-object", new ExtStandardExpressionDialect(markdown2Html));
        springTemplateEngine.addDialect("data-dialect", new DataDialect());
        return springTemplateEngine;
    }




    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public ITemplateResolver memoryTemplateResolver(TemplateService templateService) {
        MemoryTemplateResolver templateResolver = new MemoryTemplateResolver(templateService);
        templateResolver.setOrder(Ordered.LOWEST_PRECEDENCE-10);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(true);
        return templateResolver;
    }

}
