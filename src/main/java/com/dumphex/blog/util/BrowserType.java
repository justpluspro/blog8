package com.dumphex.blog.util;

/**
 * 浏览器类型
 * @author liqiwen
 * @since 2.3
 */
public enum BrowserType {
    IE11(true),
    IE10(true),
    IE9(true),
    IE8(true),
    IE7(true),
    IE6(true),
    Firefox(true),
    Safari(false),
    Chrome(true),
    Opera(true),
    Camino(true),
    Gecko(true),
    ;

    boolean supportWebp;

    BrowserType(boolean supportWebp) {
        this.supportWebp = supportWebp;
    }
}