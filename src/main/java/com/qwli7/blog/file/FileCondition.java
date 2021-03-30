package com.qwli7.blog.file;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否开启文件系统
 * @author liqiwen
 * @since 1.2
 */
public class FileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext,
                           AnnotatedTypeMetadata annotatedTypeMetadata) {
        return "true".equals(conditionContext.getEnvironment().getProperty("blog.core.fileEnabled",
                "true"));
    }
}
