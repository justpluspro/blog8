package com.qwli7.blog.template;

import com.qwli7.blog.template.data.AbstractDataProvider;
import com.qwli7.blog.template.data.ArticleDataProvider;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Data 标签方言
 * @author liqiwen
 * @since 2.5
 */
public class DataDialect extends AbstractProcessorDialect {

    private final Map<String, AbstractDataProvider<?>> dataProviderMap;


    /**
     * <data name=''></data>
     * this example: prefix = data
     */
    private static final String PREFIX = "data";

    private static final Set<IProcessor> processors = new HashSet<>();


    public DataDialect() {
        super("template dialect",  //Dialect name
                "hello", //Dialect prefix.  (hello:*)
                1000); //Dialect precedence
        dataProviderMap = new HashMap<>();
        ArticleDataProvider articleDataProvider = new ArticleDataProvider(null);
        dataProviderMap.put(articleDataProvider.getName(), articleDataProvider);
    }


    /**
     * Initialize the dialect's processors.
     *
     * Note the dialect prefix is passed here because, although we set
     * "hello" to be the dialect's prefix at the constructor, that only
     * works as a default, and at engine configuration time the user
     * might have chosen a different prefix to be used.
     */
    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new SayToAttributeTagProcessor(dialectPrefix));
        return processors;
    }

}
