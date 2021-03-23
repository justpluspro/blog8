package com.qwli7.blog.web;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BlogErrorController extends BasicErrorController {


    public BlogErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }


    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return super.error(request);
    }
}
