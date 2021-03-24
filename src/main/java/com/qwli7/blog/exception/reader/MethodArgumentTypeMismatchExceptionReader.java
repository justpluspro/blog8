package com.qwli7.blog.exception.reader;

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
    @Override
    public boolean match(Exception e) {
        return e instanceof MethodArgumentTypeMismatchException;
    }

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return Collections.singletonMap("error", "method argument type mismatch");
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
