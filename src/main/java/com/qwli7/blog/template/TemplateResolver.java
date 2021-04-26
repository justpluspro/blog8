package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.cache.AlwaysValidCacheEntryValidity;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.templateresource.SpringResourceTemplateResource;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.thymeleaf.templateresource.ITemplateResource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/25 12:34
 * 功能：TemplateResolver 模板解析器
 **/
public class TemplateResolver implements ITemplateResolver {

    private final TemplateService templateService;

    public TemplateResolver(TemplateService templateService) {
        this.templateService = templateService;
    }


    @Override
    public String getName() {
        return "blog template resolver";
    }

    @Override
    public Integer getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public TemplateResolution resolveTemplate(IEngineConfiguration iEngineConfiguration,
                                              String ownerTemplate, String template,
                                              Map<String, Object> map) {
        final Optional<Template> templateOp = templateService.findByName(template);
        Template template1 = new Template();
        template1.setContent("<html><head></head><body><h1>模板</h1></body></html>");
        template1.setName("index");

        return new TemplateResolution(new TemplateResource(template1), TemplateMode.HTML, AlwaysValidCacheEntryValidity.INSTANCE);
    }

    public static final class TemplateResource implements ITemplateResource {

        private final Template template;

        public TemplateResource(Template template) {
            this.template = template;
        }


        @Override
        public String getDescription() {
            return template.getDescription();
        }

        @Override
        public String getBaseName() {
            return template.getName();
        }

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        public Reader reader() throws IOException {
            return new StringReader(template.getContent());
        }

        @Override
        public ITemplateResource relative(String s) {
            return null;
        }

        public Template getTemplate() {
            return template;
        }
    }
}
