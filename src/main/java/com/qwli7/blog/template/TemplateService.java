package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;

public interface TemplateService {
    Template findTemplate(String templateName);

    void registerTemplate(Template template);
}
