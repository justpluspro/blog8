package com.qwli7.blog.exception.reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.Collections;
import java.util.Map;

/**
 * @author qwli7 
 * @date 2021/3/24 8:52
 * 功能：blog
 **/
public class BindExceptionReader implements ExceptionReader {
    @Override
    public boolean match(Exception e) {
        return e instanceof BindException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return Collections.emptyMap();
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
