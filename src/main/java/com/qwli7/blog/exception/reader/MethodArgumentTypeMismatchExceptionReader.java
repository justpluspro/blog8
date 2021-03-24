package com.qwli7.blog.exception.reader;

import com.qwli7.blog.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author qwli7
 * @date 2021/3/24 8:38
 * 功能：实际参数类型与给定参数类型不匹配异常
 * 实际 int  /api/tag/1
 * 给定  /api/tag/fds    fds 字符串 与 int 类型不匹配
 **/
public class MethodArgumentTypeMismatchExceptionReader implements ExceptionReader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public boolean match(Exception e) {
        return e instanceof MethodArgumentTypeMismatchException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = (MethodArgumentTypeMismatchException) ex;
        logger.error("method<readErrors>: [{}]", ex.getMessage(), ex);
        Message error = new Message();
        error.setCode("invalid.argumentType");
        error.setMessage("无效的参数类型");
        return Collections.singletonMap("errors", error);
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
