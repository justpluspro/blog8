package com.qwli7.blog.template;

import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.CategoryService;
import com.qwli7.blog.service.MomentService;
import com.qwli7.blog.template.data.*;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templatemode.TemplateMode;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * data 标签处理器
 * 用于处理 <data ></data> 标签
 * @author liqiwen
 * @since 3.0
 */
public class DataElementTagProcessor extends AbstractElementTagProcessor {

    private Map<String, AbstractDataProvider<?>> dataProviderMap;

    public DataElementTagProcessor(String dialectPrefix) {
        super(TemplateMode.HTML,
                dialectPrefix, // 标签前缀：即  xxx:text，这里我们的前缀是 data
                null,  // 匹配标签元素名称，如果是 div，则自定义的属性只能使用在 div 中，这里我们自定了 tag
                false, //标签名称是否要求有前缀
                null,  // 自定义标签属性名称
                false, //属性名称是否要求有前缀，如果为 true，Thymeleaf 会要求使用标签时必须加上前缀
                StandardDialect.PROCESSOR_PRECEDENCE);
        dataProviderMap = new HashMap<>();
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext,
                             IProcessableElementTag iProcessableElementTag,
                             IElementTagStructureHandler iElementTagStructureHandler) {
        try {
            final IAttribute[] allAttributes = iProcessableElementTag.getAllAttributes();
            Map<String, String> attributes = resolveAttr(allAttributes);
            if (attributes.isEmpty()) {
                return;
            }
            String dataProviderName = "";
            if (attributes.containsKey("name")) {
                dataProviderName = attributes.get("name");
                if (StringUtils.isEmpty(dataProviderName)) {
                    if (attributes.containsKey("alias")) {
                        dataProviderName = attributes.get("alias");
                    }
                }
            }
            if (StringUtils.isEmpty(dataProviderName)) {
                return;
            }
            final AbstractDataProvider<?> dataProvider = dataProviderMap.get(dataProviderName);
            attributes.remove("name");
            attributes.remove("alias");

            if (dataProvider == null) {
                return;
            }

            WebEngineContext engineContext = (WebEngineContext) iTemplateContext;
            final HttpServletRequest request = engineContext.getRequest();
            Map<String, Object> variables = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            mergeVariables(attributes, variables);
            final Object data = dataProvider.queryData(attributes);
            request.setAttribute(dataProviderName, data);

        } finally {
            iElementTagStructureHandler.removeElement();
        }
    }

    private void mergeVariables(Map<String, String> attributes, Map<String, Object> variables) {
        for(String key: variables.keySet()) {
            attributes.put(key, String.valueOf(variables.get(key)));
        }
    }

    private Map<String, String> resolveAttr(IAttribute[] allAttributes) {
        if(allAttributes == null || allAttributes.length == 0) {
            return new HashMap<>();
        }
        Map<String, String> attributes = new HashMap<>();
        for(IAttribute attribute: allAttributes) {
            final String attributeName =
                    attribute.getAttributeDefinition().getAttributeName().getAttributeName();
            final String value = attribute.getValue();
            attributes.putIfAbsent(attributeName, value);
        }
        return attributes;
    }

    public void registerAllDataProvider(ApplicationContext applicationContext) {
        final ArticleService articleService = applicationContext.getBean(ArticleService.class);
        final MomentService momentService = applicationContext.getBean(MomentService.class);
        final CategoryService categoryService = applicationContext.getBean(CategoryService.class);

        final ArticlesDataProvider articlesDataProvider = new ArticlesDataProvider(articleService);
        final ArticleDataProvider articleDataProvider = new ArticleDataProvider(articleService);
        final MomentsDataProvider momentsDataProvider = new MomentsDataProvider(momentService);
        final MomentDataProvider momentDataProvider = new MomentDataProvider(momentService);
        final LatestMomentsDataProvider latestMomentsDataProvider = new LatestMomentsDataProvider(momentService);
        final CategoriesDataProvider categoriesDataProvider = new CategoriesDataProvider(categoryService);
        final ArticleNavDataProvider articleNavDataProvider = new ArticleNavDataProvider(articleService);
        final MomentNavDataProvider momentNavDataProvider = new MomentNavDataProvider(momentService);

        dataProviderMap.put(momentDataProvider.getName(), momentDataProvider);
        dataProviderMap.put(momentsDataProvider.getName(), momentsDataProvider);
        dataProviderMap.put(articleDataProvider.getName(), articleDataProvider);
        dataProviderMap.put(articlesDataProvider.getName(), articlesDataProvider);
        dataProviderMap.put(latestMomentsDataProvider.getName(), latestMomentsDataProvider);
        dataProviderMap.put(categoriesDataProvider.getName(), categoriesDataProvider);
        dataProviderMap.put(articleNavDataProvider.getName(), articleNavDataProvider);
        dataProviderMap.put(momentNavDataProvider.getName(), momentNavDataProvider);

    }
}
