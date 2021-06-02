package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.cache.AlwaysValidCacheEntryValidity;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/25 12:34
 * 功能：TemplateResolver 模板解析器
 * 基于内存的模板解析
 **/
public class MemoryTemplateResolver implements ITemplateResolver {

    /**
     * 模板模式
     */
    public TemplateMode templateMode = TemplateMode.HTML;

    /**
     * 是否可缓存
     */
    private boolean cacheable = false;

    /**
     * 模板编码，默认为 UTF-8
     */
    private String characterEncoding = Charset.defaultCharset().name();

    private final TemplateService templateService;

    public MemoryTemplateResolver(TemplateService templateService) {
        this.templateService = templateService;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setTemplateMode(TemplateMode templateMode) {
        this.templateMode = templateMode;
    }

    public TemplateMode getTemplateMode() {
        return templateMode;
    }

    private int order;

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String getName() {
        return "blog template resolver";
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    /**
     * 解析模板资源
     * @param iEngineConfiguration iEngineConfiguration 模板配置
     * @param ownerTemplate ownerTemplate
     * @param templateName templateName 模板名称
     * @param map Map
     * @return TemplateResolution
     */
    @Override
    public TemplateResolution resolveTemplate(IEngineConfiguration iEngineConfiguration,
                                              String ownerTemplate, String templateName,
                                              Map<String, Object> map) {
        final Optional<Template> templateOp = templateService.findByName(templateName);
        if (templateOp.isPresent()) {
            final Template template = templateOp.get();
            return new TemplateResolution(new MemoryTemplateResource(template),
                    TemplateMode.HTML, AlwaysValidCacheEntryValidity.INSTANCE);
        }
        return null;
    }
}
