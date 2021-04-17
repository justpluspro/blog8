package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import com.qwli7.blog.mapper.TemplateMapper;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateMapper templateMapper;

    public TemplateServiceImpl(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }


    @Override
    public Template findTemplate(String templateName) {
        Template template = new Template();
        template.setContent("");
        template.setName("index");
        return template;
    }

    @Override
    public void registerTemplate(Template template) {
//        if(templateMapper.findByName(template.getName()).isPresent()) {
//            throw new RuntimeException("");
//        }
//        templateMapper.insert(template);
    }

}
