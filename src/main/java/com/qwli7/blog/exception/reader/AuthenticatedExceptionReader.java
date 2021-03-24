package com.qwli7.blog.exception.reader;

import com.qwli7.blog.exception.AuthenticatedException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class AuthenticatedExceptionReader implements ExceptionReader {
    @Override
    public boolean match(Exception e) {
        return e instanceof AuthenticatedException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        final AuthenticatedException authenticatedException = (AuthenticatedException) ex;
        final String message = authenticatedException.getMessage();

        if(StringUtils.hasText(message)) {
            return Collections.singletonMap(ERROR_KEY, message);
        }
        return Collections.singletonMap("error", "认证失败");
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_UNAUTHORIZED;
    }
}
