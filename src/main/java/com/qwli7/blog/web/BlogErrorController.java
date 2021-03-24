package com.qwli7.blog.web;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BlogErrorController extends BasicErrorController {

    private final ErrorAttributes errorAttributes;


    public BlogErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
        this.errorAttributes = errorAttributes;
    }


    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        if(status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errors = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        return new ResponseEntity<>(errors, status);
    }

    @Override
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    @Override
    public String getErrorPath() {
        return super.getErrorPath();
    }
}
