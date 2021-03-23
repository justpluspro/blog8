package com.qwli7.blog.exception.reader;

import com.qwli7.blog.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LogicExceptionReader implements ExceptionReader {
    @Override
    public boolean match(Exception e) {
        return e instanceof LogicException;
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
