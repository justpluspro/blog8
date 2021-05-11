package com.qwli7.blog.template.dialect;

import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.template.helper.Dates;
import com.qwli7.blog.template.helper.Jsoups;
import com.qwli7.blog.template.helper.Md2Html;
import com.qwli7.blog.template.helper.Pages;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author qwli7
 * @date 2021/3/3 13:56
 * 功能：ExtStandardExpressionFactory
 **/
public class ExtStandardExpressionFactory implements IExpressionObjectFactory {
    private static final String PAGE_EVALUATION_VARIABLE_NAME = "page";
    private static final String DATE_EVALUATION_VARIABLE_NAME = "datetime";
    private static final String M2H_EVALUATION_VARIABLE_NAME = "m2h";
    private static final String JSOUP_EVALUATION_VARIABLE_NAME = "joups";
    private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(PAGE_EVALUATION_VARIABLE_NAME,
                    DATE_EVALUATION_VARIABLE_NAME,
                    M2H_EVALUATION_VARIABLE_NAME,
                    JSOUP_EVALUATION_VARIABLE_NAME))
    );

    private final Markdown2Html markdown2Html;

    public ExtStandardExpressionFactory(Markdown2Html markdown2Html) {
        this.markdown2Html = markdown2Html;
    }


    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext iExpressionContext, String expressionObjectName) {
        if(PAGE_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new Pages();
        }
        if(DATE_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new Dates();
        }
        if (M2H_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new Md2Html(markdown2Html);
        }
        if(JSOUP_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new Jsoups();
        }
        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return expressionObjectName != null && !"object".equals(expressionObjectName);
    }
}
