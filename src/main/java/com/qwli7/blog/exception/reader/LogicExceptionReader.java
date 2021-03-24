package com.qwli7.blog.exception.reader;

import com.qwli7.blog.Message;
import com.qwli7.blog.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author liqiwen
 * @since 1.2
 */
public class LogicExceptionReader implements ExceptionReader {
    @Override
    public boolean match(Exception e) {
        return e instanceof LogicException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        final LogicException logicException = (LogicException) ex;
        final Message error = logicException.getError();
        return Collections.singletonMap(ERROR_KEY, error);
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
