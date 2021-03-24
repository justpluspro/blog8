package com.qwli7.blog;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/26 14:07
 * 功能：blog
 **/
public class BlogContext {
    private static final ThreadLocal<Boolean> AUTHENTICATE_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<String> IP_THREAD_LOCAL  = new ThreadLocal<>();


    public static void setAuthenticated(boolean authenticate) {
        AUTHENTICATE_THREAD_LOCAL.set(authenticate);
    }


    public static void setIp(String ip) {
        IP_THREAD_LOCAL.set(ip);
    }

    public static Optional<String> getIp() {
        return Optional.of(IP_THREAD_LOCAL.get());
    }


    public static void clear() {
        AUTHENTICATE_THREAD_LOCAL.remove();
        IP_THREAD_LOCAL.remove();
    }

    public static Boolean isAuthenticated() {
        return AUTHENTICATE_THREAD_LOCAL.get() != null && AUTHENTICATE_THREAD_LOCAL.get();
    }
}
