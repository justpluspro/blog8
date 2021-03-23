package com.qwli7.blog.exception.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 方法不支持时走此异常
 * @author liqiwen
 * @since 1.2
 */
public class HttpRequestMethodNotSupportedExceptionReader implements ExceptionReader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Override
    public boolean match(Exception e) {
        return e instanceof HttpRequestMethodNotSupportedException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        final HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException = (HttpRequestMethodNotSupportedException) ex;
        logger.info("method<readErrors> [{}]", httpRequestMethodNotSupportedException.toString());

        return null;
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
    }
}
