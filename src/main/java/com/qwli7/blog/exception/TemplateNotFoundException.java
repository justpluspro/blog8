package com.qwli7.blog.exception;

/**
 * 模板未找到异常
 * @author liqiwen
 * @since 3.1
 */
public class TemplateNotFoundException extends RuntimeException {


    public TemplateNotFoundException(String msg){
        super(msg);
    }

}
