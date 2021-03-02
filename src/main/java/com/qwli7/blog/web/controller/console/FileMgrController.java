package com.qwli7.blog.web.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qwli7
 * @date 2021/3/2 8:03
 * 功能：blog
 **/
@Controller
@RequestMapping("console")
public class FileMgrController {

    @GetMapping("files")
    public String index() {
        return "console/file/index";
    }
}
