package com.qwli7.blog.security;

import java.io.Serializable;
import java.util.UUID;

/**
 * TokenUtil 工具类
 * @author liqiwen
 * @since 1.2
 */
public class TokenUtil implements Serializable {

    private static final Long LIVE = 30 * 60 * 1000L;
    private static AuthToken authToken;

    public TokenUtil() {
        super();
    }

    /**
     * 创建一个新的 token
     * @return String
     */
    public static String createNew() {
        authToken = new AuthToken();
        return authToken.token;
    }

    /**
     * 校验 token 是否有效
     * @param str str
     * @param increaseTime increaseTime
     * @return true | false
     */
    public static boolean valid(String str, boolean increaseTime) {
        return authToken != null && authToken.valid(str, increaseTime);
    }

    /**
     * 校验 token
     * @param str str
     * @return true | false
     */
    public static boolean valid(String str) {
        return valid(str, true);
    }

    /**
     * 移除 token
     */
    public static void remove() {
        authToken = null;
    }

    /**
     * Token
     */
    public static class AuthToken implements Serializable {

        private final String token;

        private long expireTime;

        public AuthToken() {
            this.token = UUID.randomUUID().toString().replace("-", "");
            this.expireTime = System.currentTimeMillis();
        }

        public boolean valid(String token, boolean increaseTime) {
            long now = System.currentTimeMillis();
            if(now - expireTime > LIVE) {
                return false;
            }
            if(token != null && token.equals(this.token)) {
                if(increaseTime) {
                    this.expireTime = now;
                }
                return true;
            }
            return false;
        }
    }

}
