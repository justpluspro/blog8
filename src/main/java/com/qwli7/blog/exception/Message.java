package com.qwli7.blog.exception;

/**
 * @author qwli7 
 * @date 2023/2/16 15:21
 * 功能：blog8
 **/
public enum Message {

    AUTH_FAILED("auth.failed", "认证失败"),
    CATEGORY_NOT_EXISTS("category.notExists", "分类不存在"),
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
