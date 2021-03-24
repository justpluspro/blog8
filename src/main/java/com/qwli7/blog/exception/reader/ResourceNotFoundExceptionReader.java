package com.qwli7.blog.exception.reader;

import com.qwli7.blog.exception.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ResourceNotFoundExceptionReader implements ExceptionReader {
    @Override
    public boolean match(Exception e) {
        return e instanceof ResourceNotFoundException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        ResourceNotFoundException resourceNotFoundException = (ResourceNotFoundException) ex;
        return null;
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return 0;
    }
}
