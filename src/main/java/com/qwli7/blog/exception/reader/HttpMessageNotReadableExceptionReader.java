package com.qwli7.blog.exception.reader;

import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HttpMessageNotReadableExceptionReader implements ExceptionReader {
    @Override
    public boolean match(Exception e) {
        return e instanceof HttpMessageNotReadableException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return null;
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
