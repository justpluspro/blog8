package com.qwli7.blog.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qwli7 
 * @date 2023/2/16 15:41
 * 功能：blog8
 **/
@ControllerAdvice
public class BlogExceptionHandler {


    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Map<String, Object> handleBizException(BizException bizException) {
        Message error = bizException.getError();
        Map<String, Object> data = new HashMap<>();
        data.put("code", error.getCode());
        data.put("message", error.getDesc());
        return data;
    }
}
