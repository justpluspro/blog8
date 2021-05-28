package com.qwli7.blog.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Web 处理工具类
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
public class WebUtils {
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    private static final String USER_AGENT = "USER-AGENT";
    private final static String IE11 = "rv:11.0";
    private final static String IE10 = "MSIE 10.0";
    private final static String IE9 = "MSIE 9.0";
    private final static String IE8 = "MSIE 8.0";
    private final static String IE7 = "MSIE 7.0";
    private final static String IE6 = "MSIE 6.0";
    private final static String MAXTHON = "Maxthon";
    private final static String QQ = "QQBrowser";
    private final static String GREEN = "GreenBrowser";
    private final static String SE360 = "360SE";
    private final static String FIREFOX = "Firefox";
    private final static String OPERA = "Opera";
    private final static String CHROME = "Chrome";
    private final static String SAFARI = "Safari";
    private final static String OTHER = "其它";

    private WebUtils(){
        super();
    }

    /**
     * 浏览器是否支持 webp
     * @param request requsest
     * @return boolean
     */
    public static boolean isSupportWebp(HttpServletRequest request) {
        final BrowserType browserType = getBrowserType(request);
        return browserType != null && browserType.supportWebp;
    }

    /**
     * 获取浏览器类型
     *
     * @param request request
     * @return BrowserType
     */
    public static BrowserType getBrowserType(HttpServletRequest request) {
        BrowserType browserType = null;
        if (getBrowserType(request, IE11)) {
            browserType = BrowserType.IE11;
        }
        if (getBrowserType(request, IE10)) {
            browserType = BrowserType.IE10;
        }
        if (getBrowserType(request, IE9)) {
            browserType = BrowserType.IE9;
        }
        if (getBrowserType(request, IE8)) {
            browserType = BrowserType.IE8;
        }
        if (getBrowserType(request, IE7)) {
            browserType = BrowserType.IE7;
        }
        if (getBrowserType(request, IE6)) {
            browserType = BrowserType.IE6;
        }
        if (getBrowserType(request, FIREFOX)) {
            browserType = BrowserType.Firefox;
        }
        if (getBrowserType(request, SAFARI)) {
            browserType = BrowserType.Safari;
        }
        if (getBrowserType(request, CHROME)) {
            browserType = BrowserType.Chrome;
        }
        if (getBrowserType(request, OPERA)) {
            browserType = BrowserType.Opera;
        }
        if (getBrowserType(request, "Camino")) {
            browserType = BrowserType.Camino;
        }
        return browserType;
    }

    private static boolean getBrowserType(HttpServletRequest request, String browerType) {
        return request.getHeader(USER_AGENT).toLowerCase().indexOf(browerType) > 0;
    }

    public static String checkBrowser(HttpServletRequest request) {
        final String userAgent = request.getHeader(USER_AGENT);
        if (regex(OPERA, userAgent)) {
            return OPERA;
        }
        if (regex(CHROME, userAgent)) {
            return CHROME;
        }
        if (regex(FIREFOX, userAgent)) {
            return FIREFOX;
        }
        if (regex(SAFARI, userAgent)) {
            return SAFARI;
        }
        if (regex(SE360, userAgent)) {
            return SE360;
        }
        if (regex(GREEN, userAgent)) {
            return GREEN;
        }
        if (regex(QQ, userAgent)) {
            return QQ;
        }
        if (regex(MAXTHON, userAgent)) {
            return MAXTHON;
        }
        if (regex(IE11, userAgent)) {
            return IE11;
        }
        if (regex(IE10, userAgent)) {
            return IE10;
        }
        if (regex(IE9, userAgent)) {
            return IE9;
        }
        if (regex(IE8, userAgent)) {
            return IE8;
        }
        if (regex(IE7, userAgent)) {
            return IE7;
        }
        if (regex(IE6, userAgent)) {
            return IE6;
        }
        return OTHER;
    }

    private static boolean regex(String regex, String str) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(str);
        return m.find();
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
