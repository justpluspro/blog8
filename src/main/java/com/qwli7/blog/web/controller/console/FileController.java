package com.qwli7.blog.web.controller.console;

import com.qwli7.blog.plugin.file.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qwli7
 * @date 2023/2/20 13:14
 * 功能：blog8
 **/
@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("console/files")
    public String filesIndex() {
        return "console/files";
    }

    @GetMapping("console/file/edit")
    public String fileEdit(@RequestParam("path") String path) {
        return "console/file_edit";
    }
}
