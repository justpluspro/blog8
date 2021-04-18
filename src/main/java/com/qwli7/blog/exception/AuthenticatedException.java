package com.qwli7.blog.exception;

/**
 * 认证失败异常
 * @author liqiwen
 * @since 2.1
 */
public class AuthenticatedException extends RuntimeException {

    public AuthenticatedException () {

    }

    public AuthenticatedException(String msg) {
        super(msg);
    }
}
