package com.qwli7.blog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(value = {"com.qwli7.blog.web.controller"})
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @ExceptionHandler(LogicException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleLogicException(LogicException e){
        logger.info("method<handleLogicException> e:[{}]", e.getError());
        return new Object();
    }

}
