package com.qwli7.blog.exception.reader;

import com.qwli7.blog.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author qwli7 
 * @date 2021/3/24 8:39
 * 功能：blog
 **/
public class IllegalArgumentExceptionReader implements ExceptionReader {
    @Override
    public boolean match(Exception e) {
        return e instanceof IllegalArgumentException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        IllegalArgumentException argumentException = (IllegalArgumentException) ex;
        String message = argumentException.getMessage();
        Message error = new Message();
        error.setCode("illegal.argument");
        error.setMessage(message);
        return Collections.singletonMap("errors", error);
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
