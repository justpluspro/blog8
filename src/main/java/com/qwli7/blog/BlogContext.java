package com.qwli7.blog;

import com.qwli7.blog.entity.GlobalConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2023/2/17 9:18
 * 功能：blog8
 **/
public class BlogContext {

    private static final ThreadLocal<Boolean> loginThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> IP_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存储用户的全局配置
     */
    private static final Map<String, GlobalConfig> globalConfigMap = new ConcurrentHashMap<>();

    public static void setAuthorized(Boolean authorized) {
        loginThreadLocal.set(authorized);
    }

    public static boolean isAuthorized() {
        return loginThreadLocal.get();
    }


    public static void setIp(String ip) {
        IP_THREAD_LOCAL.set(ip);
    }

    public static String getIp() {
        return IP_THREAD_LOCAL.get();
    }

    public static void updateGlobalConfig(GlobalConfig globalConfig) {
        globalConfigMap.put("config", globalConfig);
    }

    public static GlobalConfig getGlobalConfig() {
        return globalConfigMap.getOrDefault("config", null);
    }
}
