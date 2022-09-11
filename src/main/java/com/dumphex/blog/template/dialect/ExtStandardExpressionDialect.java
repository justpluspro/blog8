package com.dumphex.blog.template.dialect;

import com.dumphex.blog.service.Markdown2Html;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * @author qwli7
 * @date 2021/3/3 13:55
 * 功能：ExtStandardExpressionDialect
 **/
public class ExtStandardExpressionDialect extends AbstractDialect implements IExpressionObjectDialect {

    private final Markdown2Html markdown2Html;

    public ExtStandardExpressionDialect(Markdown2Html markdown2Html) {
        super("ext-standard-expression");
        this.markdown2Html = markdown2Html;
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new ExtStandardExpressionFactory(markdown2Html);
    }
}
