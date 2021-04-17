package com.qwli7.blog.template;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class MyAutoDialect extends AbstractProcessorDialect {

    /**
     * <data name=''></data>
     * this example: prefix = data
     */
    private static final String PREFIX = "data";

    private static final Set<IProcessor> processors = new HashSet<>();


    public MyAutoDialect() {
        super("template dialect",  //Dialect name
                "hello", //Dialect prefix.  (hello:*)
                1000); //Dialect precedence
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
