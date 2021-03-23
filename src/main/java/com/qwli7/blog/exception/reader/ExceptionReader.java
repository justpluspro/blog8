package com.qwli7.blog.exception.reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 异常读取器
 */
public interface ExceptionReader {


    boolean match(Exception e);


    Map<String, Object> readErrors(Exception ex);


    int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex);
}
