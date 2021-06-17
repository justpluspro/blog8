package com.qwli7.blog.exception;

import com.qwli7.blog.exception.reader.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 自定义异常解析器
 * @author liqiwen
 */
public class BlogExceptionResolver implements ErrorAttributes, HandlerExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final String ERROR_ATTRIBUTE = this.getClass().getName() + ".ERROR";
    private final String SERVLET_ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    private final String SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    private final List<ExceptionReader> exceptionReaders;

    public BlogExceptionResolver() {
        logger.info("class BlogExceptionResolver constructor method initial success!");
        this.exceptionReaders = new ArrayList<>();
        this.exceptionReaders.add(new LogicExceptionReader());
        this.exceptionReaders.add(new AuthenticatedExceptionReader());
        // json 格式提交数据时走此异常
        this.exceptionReaders.add(new MethodArgumentNotValidExceptionReader());
        //表单提交时走此异常
        this.exceptionReaders.add(new ConstraintViolationExceptionReader());
        // 方法不支持时走此异常
        this.exceptionReaders.add(new HttpRequestMethodNotSupportedExceptionReader());
        // 传入类型和目标类型不匹配异常
        this.exceptionReaders.add(new MethodArgumentTypeMismatchExceptionReader());
        this.exceptionReaders.add(new IllegalArgumentExceptionReader());
        this.exceptionReaders.add(new BindExceptionReader());
        this.exceptionReaders.add(new LoginFailExceptionReader());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        logger.info("exception occurred! e:[{}]", e.getMessage(), e);

        Optional<ExceptionReader> exceptionReaderOp = filterReader(e);
        if(exceptionReaderOp.isPresent()) {
            ExceptionReader exceptionReader = exceptionReaderOp.get();
            final Map<String, Object> stringObjectMap = exceptionReader.readErrors(e);

            httpServletRequest.removeAttribute(ERROR_ATTRIBUTE);

            int statusCode = exceptionReader.getStatus(httpServletRequest, httpServletResponse, e);
            httpServletRequest.setAttribute(SERVLET_ERROR_STATUS_CODE, statusCode);
            httpServletRequest.setAttribute(ERROR_ATTRIBUTE, stringObjectMap);

            return null;
        }


        return new ModelAndView();
    }


    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
//        errorAttributes.put("timestamp", LocalDateTime.now());
//        this.addErrorDetail(errorAttributes, webRequest);
//        this.addRequestUri(errorAttributes, webRequest);

        return (Map<String, Object>) webRequest.getAttribute(ERROR_ATTRIBUTE, 0);
    }


    @SuppressWarnings("unchecked")
    private void addErrorDetail(Map<String, Object> errorAttributes, WebRequest webRequest) {
        Map<String, Object> error = (Map<String, Object>) webRequest.getAttribute(ERROR_ATTRIBUTE, 0);
        Assert.notNull(error, "error must be not null");
        errorAttributes.put("errors", error.getOrDefault("errors", Collections.emptyMap()));
    }


    private void addRequestUri(Map<String, Object> errorAttributes, WebRequest webRequest) {
        String path = (String) webRequest.getAttribute(SERVLET_ERROR_REQUEST_URI, 0);
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }


    public Optional<ExceptionReader> filterReader(Exception e){
        return this.exceptionReaders.stream().filter(exceptionReader -> exceptionReader.match(e)).findFirst();
    }

    @Override
    public Throwable getError(WebRequest webRequest) {
        return null;
    }


}
