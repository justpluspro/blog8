package com.qwli7.blog.exception.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

/**
 * POST 请求表单提交时会走此异常，校验失败
 * @author liqiwen
 * @since 1.2
 */
public class ConstraintViolationExceptionReader implements ExceptionReader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Override
    public boolean match(Exception e) {
        return e instanceof ConstraintViolationException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
        final Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        logger.info("method<readErrors> constraintViolations: [{}]", constraintViolations);
        return null;
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
