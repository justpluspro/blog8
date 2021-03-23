package com.qwli7.blog.exception;

public class AuthenticatedException extends RuntimeException {

    public AuthenticatedException () {

    }

    public AuthenticatedException(String msg) {
        super(msg);
    }
}
