package com.qwli7.blog.util;

/**
 * 系统工具类
 * @author liqiwen
 * @since 2.2
 */
public class SystemUtils {

    /**
     * 获取系统类型
     * @return SystemType
     */
    public static SystemType getSystemType() {
        final String property = System.getProperty("os.name");
        if(property.toLowerCase().startsWith("win")) {
            return SystemType.WINDOWS;
        }
        return SystemType.LINUX;
    }
}
