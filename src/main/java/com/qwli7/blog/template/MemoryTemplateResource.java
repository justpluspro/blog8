package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import org.thymeleaf.templateresource.ITemplateResource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * 模板资源
 * @author liqiwen
 * @since 2.0
 */
public class MemoryTemplateResource implements ITemplateResource {

    private final Template template;

    public MemoryTemplateResource(Template template) {
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
