package com.qwli7.blog.template;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.spring5.util.SpringVersionUtils;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class SayToAttributeTagProcessor extends AbstractAttributeTagProcessor {

    private final static String ATTR_NAME = "sayto";
    private final static int PRECEDENCE = 1000;

    protected SayToAttributeTagProcessor(String dialectPrefix) {
        super(TemplateMode.HTML,  // This processor will apply only to HTML mode
                dialectPrefix, // prefix to be applied to name for matching
                 null, //no tag name: match any tag name
                false, //no prefix to be applied to tag name
                ATTR_NAME, // Name of the attribute that will be matched
                true, //Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true);  //remove the matched attribute afterwards
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext,
                             IProcessableElementTag iProcessableElementTag,
                             AttributeName attributeName, String attributeValue,
                             IElementTagStructureHandler iElementTagStructureHandler) {

        final int springVersionMajor = SpringVersionUtils.getSpringVersionMajor();
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);

        final IModelFactory modelFactory = iTemplateContext.getModelFactory();


        final IEngineConfiguration configuration = iTemplateContext.getConfiguration();
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression iStandardExpression = expressionParser.parseExpression(iTemplateContext, attributeValue);

        final String execute = (String) iStandardExpression.execute(iTemplateContext);



        iElementTagStructureHandler.setBody("Hello, " + HtmlEscape.escapeHtml5(attributeValue) + "!",
                false);
    }
}
