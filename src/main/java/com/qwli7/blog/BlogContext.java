package com.qwli7.blog;

/**
 * @author qwli7
 * @date 2021/2/26 14:07
 * 功能：blog
 **/
public class BlogContext {
    private static final ThreadLocal<Boolean> authenticateMap = new ThreadLocal<>();


    public static void setAuthenticated(boolean authenticate) {
        authenticateMap.set(authenticate);
    }


    public static void clear() {
        authenticateMap.remove();
    }
}
