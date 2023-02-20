package com.qwli7.blog.web.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qwli7
 * @date 2023/2/20 13:14
 * 功能：blog8
 **/
@Controller
public class FileController {


    @GetMapping("/console/files")
    public String filesIndex() {
        return "console/files";
    }
}
