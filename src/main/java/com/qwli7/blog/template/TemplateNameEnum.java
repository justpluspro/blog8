package com.qwli7.blog.template;

import java.util.Arrays;
import java.util.Optional;

/**
 * 模板常量
 * @author liqiwen
 * @since 2.1
 */
public enum TemplateNameEnum {

    INDEX("index", "/"),
    ARTICLE("article", "/article/{idOrAlias}"),
    MOMENT("moment", "/moment/{id}"),
    MOMENTS("moments", "/moments"),
    ;
    String name;
    String pattern;

    TemplateNameEnum(String templateNme, String pattern) {
        this.name = templateNme;
        this.pattern = pattern;

    }

    public static Optional<TemplateNameEnum> getTemplateNameEnumByName(String nameWithoutExt) {
        return Arrays.stream(TemplateNameEnum.values()).filter(e-> nameWithoutExt.equalsIgnoreCase(e.name)).findAny();
    }
}
