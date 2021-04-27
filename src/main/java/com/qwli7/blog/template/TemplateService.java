package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import com.qwli7.blog.entity.vo.TemplateQueryParam;

import java.util.List;
import java.util.Optional;

/**
 * 模板 Service
 * @author liqiwen
 * @since 2.0
 */
public interface TemplateService {

    /**
     * 获取到一个模板
     * @param templateName templateName
     * @return Template
     */
    Template findTemplate(String templateName);

    /**
     * 获取所有的模板
     * @param queryParam queryParam
     * @return List
     */
    List<Template> getAllTemplates(TemplateQueryParam queryParam);

    /**
     * 注册一个模板
     * @param template template
     */
    void registerTemplate(Template template);

    /**
     * 注册全部的模板
     * @param template template
     */
    void registerAllTemplate(Template template);

    /**
     * 根据名称获取一个模板
     * @param templateName templateName
     * @return Template
     */
    Optional<Template> findByName(String templateName);

    /**
     * 获取内存中已经全部注册的 urlPatterns
     * @return List
     */
    List<String> getAllUrlPatterns();
}
