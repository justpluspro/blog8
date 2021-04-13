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
    private static UserToken userToken;

    public TokenUtil() {
        super();
    }


    public static String createNew() {
        userToken = new UserToken();
        return userToken.token;
    }

    public static boolean valid(String str, boolean increaseTime) {
        return userToken != null && userToken.valid(str, increaseTime);
    }

    public static boolean valid(String str) {
        return valid(str, true);
    }

    public static void remove() {
        userToken = null;
    }

    public static class UserToken implements Serializable {

        private final String token;

        private long expireTime;

        public UserToken() {
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
