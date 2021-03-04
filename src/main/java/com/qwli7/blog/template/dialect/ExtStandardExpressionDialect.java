package com.qwli7.blog.template.dialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * @author qwli7
 * @date 2021/3/3 13:55
 * 功能：blog
 **/
public class ExtStandardExpressionDialect extends AbstractDialect implements IExpressionObjectDialect {

    public ExtStandardExpressionDialect() {
        super("ext-standard-expression");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new ExtStandardExpressionFactory();
    }
}
