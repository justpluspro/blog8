package com.qwli7.blog.exception.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * requestBody 里面的校验会会抛出此异常
 * ContentType: application/json 的情况下
 *
 * @author liqiwen
 * @since 1.2
 */
public class MethodArgumentNotValidExceptionReader implements ExceptionReader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public boolean match(Exception e) {
        return e instanceof MethodArgumentNotValidException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
        final BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        logger.info("method<readErrors> bindingResult:[{}]", bindingResult.toString());
        return null;
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
