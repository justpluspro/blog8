package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.templateresource.SpringResourceTemplateResource;
import org.thymeleaf.templateresource.ITemplateResource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * @author qwli7
 * @date 2021/2/25 12:34
 * 功能：blog
 **/
public class TemplateResolver extends SpringResourceTemplateResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final TemplateService templateService;
    private final ApplicationContext applicationContext;

    public TemplateResolver(TemplateService templateService, ApplicationContext applicationContext){
        this.templateService = templateService;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate,
                                                        String templateName, String resourceName,
                                                        String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        logger.info("computeTemplateResource:[{}]", configuration);
        logger.info("ownerTemplate:[{}]", ownerTemplate);
        logger.info("template:[{}]", templateName);
        logger.info("resourceName:[{}]", resourceName);
        logger.info("characterEncoding:[{}]", characterEncoding);
//        logger.info("templateResolutionAttributes:[{}]", templateResolutionAttributes.size());
        logger.info("templateResolutionAttributes:[{}]", templateResolutionAttributes);

        //从数据库中查询模板
        Template template = templateService.findTemplate(templateName);
        if(template == null) {
            return super.computeTemplateResource(configuration, ownerTemplate, templateName, resourceName, characterEncoding, templateResolutionAttributes);
            //            return new SpringResourceTemplateResource(applicationContext, templateName, characterEncoding);
        }
        ClassPathResource resource = new ClassPathResource("defaultTemplate");
        return new SpringResourceTemplateResource(resource, characterEncoding);
    }


    public static final class TemplateResource implements ITemplateResource {

        private final Template template;

        public TemplateResource(Template template) {
            this.template = template;
        }


        @Override
        public String getDescription() {
            return null;
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
