package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.cache.AlwaysValidCacheEntryValidity;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;
import java.util.Map;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/25 12:34
 * 功能：TemplateResolver 模板解析器
 * 基于内存的模板解析
 **/
public class MemoryTemplateResolver implements ITemplateResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 模板解析器顺序
     */
    private int order;

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 模板业务实现类
     */
    private final TemplateService templateService;

    public MemoryTemplateResolver(TemplateService templateService) {
        this.templateService = templateService;
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
        logger.info("method<resolveTemplate> resolve templateName:[{}]", templateName);
        final Optional<Template> templateOp = templateService.findByName(templateName);
        if (templateOp.isPresent()) {
            final Template template = templateOp.get();
            return new TemplateResolution(new MemoryTemplateResource(template),
                    TemplateMode.HTML, AlwaysValidCacheEntryValidity.INSTANCE);
        }
        return null;
    }
}
