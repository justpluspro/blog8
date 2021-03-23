package com.qwli7.blog.exception;

import com.qwli7.blog.exception.reader.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义异常解析器
 * @author liqiwen
 */
public class BlogExceptionResolver implements ErrorAttributes, HandlerExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final String ERROR_ATTRIBUTE = this.getClass().getName() + ".ERROR";

    private final List<ExceptionReader> exceptionReaders;

    public BlogExceptionResolver() {
        this.exceptionReaders = new ArrayList<>();
        this.exceptionReaders.add(new LogicExceptionReader());
        this.exceptionReaders.add(new AuthenticatedExceptionReader());
        // json 格式提交数据时走此异常
        this.exceptionReaders.add(new MethodArgumentNotValidExceptionReader());
        //表单提交时走此异常
        this.exceptionReaders.add(new ConstraintViolationExceptionReader());
        // 方法不支持时走此异常
        this.exceptionReaders.add(new HttpRequestMethodNotSupportedExceptionReader());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        logger.info("exception occurred! ");

        filterReader(e).ifPresent(exceptionReader ->  {

            final Map<String, Object> stringObjectMap = exceptionReader.readErrors(e);



        });


        return null;
    }


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  ErrorAttributeOptions options) {
        return null;
    }


    public Optional<ExceptionReader> filterReader(Exception e){
        return this.exceptionReaders.stream().filter(exceptionReader -> exceptionReader.match(e)).findFirst();
    }

    @Override
    public Throwable getError(WebRequest webRequest) {
        return null;
    }


}
