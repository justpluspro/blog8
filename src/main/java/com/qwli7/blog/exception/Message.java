package com.qwli7.blog.exception;

/**
 * @author qwli7 
 * @date 2023/2/16 15:21
 * 功能：blog8
 **/
public enum Message {

    AUTH_FAILED("auth.failed", "认证失败"),
    CATEGORY_NOT_EXISTS("category.notExists", "分类不存在"),
    ARTICLE_NOT_EXISTS("article.notExists", "文章不存在"),
    ARTICLE_ALIAS_EXISTS("articleAlias.exists", "文章别名已经存在"),
    CATEGORY_ALIAS_EXISTS("category.alias.exists", "分类别名已经存在"),

    TAG_EXISTS("tag.exists", "标签已经存在"),
    TAG_NOT_FOUND("tag.notFound", "标签不存在"),

    MOMENT_NOT_EXISTS("moment.notExists", "动态不存在"),


    OPERATOR_TOO_FREQUENCY("operator.tooFrequency", "操作太频繁"),

    NOT_LOGIN("user.notLogin", "用户未登录"),
    CAPTCHA_NOT_EMPTY("captcha.not.empty", "验证码不能为空"),
    CAPTCHA_ERROR("captcha.error", "验证码错误"),

    ARTICLE_NOT_FOUND("article.notFound", "文章未发现"),
    ;

    final String code;

    final String desc;

    Message(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
