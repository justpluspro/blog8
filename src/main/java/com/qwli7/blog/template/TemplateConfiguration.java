package com.qwli7.blog.template;

import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.template.dialect.ExtStandardExpressionDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.expression.Sets;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * 模板配置类
 * @author liqiwen
 * @since 2.2
 */
@Configuration
public class TemplateConfiguration {


    @Bean
    public SpringTemplateEngine templateEngine(Markdown2Html markdown2Html) {

        final ITemplateResolver iTemplateResolver = templateResolver();
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        Set<ITemplateResolver> templateResolvers = new HashSet<>();
        templateResolvers.add(iTemplateResolver);

        springTemplateEngine.setTemplateResolvers(templateResolvers);

        springTemplateEngine.addDialect("expression-object", new ExtStandardExpressionDialect(markdown2Html));

        return springTemplateEngine;
    }


    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setOrder(Integer.MAX_VALUE);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(true);
        return templateResolver;
    }

}
