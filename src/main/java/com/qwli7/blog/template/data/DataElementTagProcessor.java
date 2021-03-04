package com.qwli7.blog.template.data;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qwli7
 * @date 2021/3/4 8:57
 * 功能：blog
 **/
public class DataElementTagProcessor extends AbstractElementTagProcessor {
    private final static String TAG_NAME = "data";


    private Map<String, AbstractDataProvider<?>> dataProviderMap;

    public DataElementTagProcessor(String dialectPrefix) {
        //带有 <data> 元素的模式
        //方言前缀
        //标签名称
        //是否给该标签加上前缀
        //属性名称，不存在则根据标签匹配
        // No prefix to be applied to attribute name
        super(TemplateMode.HTML,
                dialectPrefix,
                TAG_NAME,
                false,
                null,
                false,
                1000
        );
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag,
                             IElementTagStructureHandler iElementTagStructureHandler) {
        try {
            //获取 spring context
            ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);
            String name = iProcessableElementTag.getAttributeValue("name");
            String alias = iProcessableElementTag.getAttributeValue("alias");

            WebEngineContext context = (WebEngineContext) iTemplateContext;
            HttpServletRequest request = context.getRequest();

            request.setAttribute("article", "这是我附加的数据");


            Map<String, String> paramAttributeMap = new HashMap<>();

            String currentPage = iProcessableElementTag.getAttributeValue("currentPage");
            if(StringUtils.isEmpty(currentPage)) {
                currentPage = "1";
            }
            String pageSize = iProcessableElementTag.getAttributeValue("pageSize");
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "10";
            }
            String query = iProcessableElementTag.getAttributeValue("query");
            paramAttributeMap.put("currentPage", currentPage);
            paramAttributeMap.put("pageSize", pageSize);
            paramAttributeMap.put("query", query);


//            if (StringUtils.isEmpty(name)) {
//                name = alias;
//            }
//            if (StringUtils.isEmpty(alias)) {
//                return;
//            }
//            DataProcessor<?> dataProcessor = dataProcessorMap.get(name);
//            Object data = dataProcessor.processData();
            AbstractDataProvider<?> dataProvider = dataProviderMap.get(name);

            Object data = dataProvider.queryData(paramAttributeMap);
//            Object data = dataProcessor.processData();

            request.setAttribute(dataProvider.getName(), data);

        } finally {
            iElementTagStructureHandler.removeElement();
        }
    }

    public void registerAllProcessors(ApplicationContext applicationContext) {
        this.dataProviderMap = new HashMap<>();
        MomentsDataProvider momentsDataProcessor = applicationContext.getBean(MomentsDataProvider.class, AbstractDataProvider.class);
        dataProviderMap.put(momentsDataProcessor.getName(), momentsDataProcessor);
        TagsDataProvider tagsDataProvider = applicationContext.getBean(TagsDataProvider.class, AbstractDataProvider.class);
        dataProviderMap.put(tagsDataProvider.getName(), tagsDataProvider);

        final ArticlesDataProvider articlesDataProvider = applicationContext.getBean(ArticlesDataProvider.class, AbstractDataProvider.class);
        dataProviderMap.put(articlesDataProvider.getName(), articlesDataProvider);



    }
}
