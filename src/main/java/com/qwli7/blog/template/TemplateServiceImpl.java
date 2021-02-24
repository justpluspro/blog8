package com.qwli7.blog.template;

import com.qwli7.blog.mapper.TemplateMapper;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateMapper templateMapper;

    public TemplateServiceImpl(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }


}
