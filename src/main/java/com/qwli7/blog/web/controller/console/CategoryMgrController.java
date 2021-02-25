package com.qwli7.blog.web.controller.console;

import com.qwli7.blog.security.Authenticated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Authenticated
@RequestMapping("console")
@Controller
public class CategoryMgrController {

    @GetMapping("categories")
    public String index() {
        return "console/category/index";
    }

}
