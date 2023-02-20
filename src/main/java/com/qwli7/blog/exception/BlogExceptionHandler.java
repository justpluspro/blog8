package com.qwli7.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author qwli7 
 * @date 2023/2/16 15:41
 * 功能：blog8
 **/
@ControllerAdvice
public class BlogExceptionHandler {

    private static final String VALIDATE_MESSAGE_TEMPLATE = "%s.validateFailed";


    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Map<String, Object> handleBizException(BizException bizException) {
        Message error = bizException.getError();
        Map<String, Object> data = new HashMap<>();
        data.put("code", error.getCode());
        data.put("message", error.getDesc());
        return data;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleMethodArgumentNotException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!CollectionUtils.isEmpty(fieldErrors)) {
            FieldError fieldError = fieldErrors.get(0);
            Map<String, Object> data = new HashMap<>();
            String code = String.format(VALIDATE_MESSAGE_TEMPLATE, fieldError.getField());
            String message = fieldError.getDefaultMessage();
            data.put("code", code);
            data.put("message", message);
            return data;
        }


        return Collections.emptyMap();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException notFoundException, HttpServletRequest request) {
        Message error = notFoundException.getError();
        request.setAttribute("error",error);
        return "error";
    }
}
