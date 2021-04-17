package com.qwli7.blog.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Web 处理工具类
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
public class WebUtils {
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private WebUtils(){
        super();
    }


    /**
     * 是否是规则的请求地址
     * @param serverUri serverUri
     * @return boolean
     */
    public static boolean isRegularUrl(String serverUri) {
        return serverUri.startsWith(HTTPS_PREFIX) || serverUri.startsWith(HTTP_PREFIX);
    }


    /**
     * 获取客户端 ip
     * @param request request
     * @return String
     */
    public String getClientIp(HttpServletRequest request) {


        return "";
    }
}
