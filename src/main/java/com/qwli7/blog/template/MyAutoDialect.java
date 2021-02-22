package com.qwli7.blog.template;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MyAutoDialect extends AbstractDialect {

    /**
     * <data name=''></data>
     * this example: prefix = data
     */
    private static final String PREFIX = "data";

    private static final Set<IProcessor> processors = new HashSet<>();

    static {
//        processor
    }

    public MyAutoDialect() {
        super("template dialect");
    }

    public String getPrefix() {
        return PREFIX;
    }

    public static Set<IProcessor> getProcessor() {
        return Collections.unmodifiableSet(processors);
    }
}
