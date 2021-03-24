package com.qwli7.blog.exception.reader;

import com.qwli7.blog.exception.LoginFailException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class LoginFailExceptionReader implements ExceptionReader {


    @Override
    public boolean match(Exception e) {
        return e instanceof LoginFailException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return Collections.singletonMap(ERROR_KEY, ((LoginFailException) ex).getError());
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_UNAUTHORIZED;
    }
}
