package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.plugin.file.FileInfo;
import com.qwli7.blog.plugin.file.FileService;
import com.qwli7.blog.plugin.file.FileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qwli7
 * @date 2023/2/17 11:27
 * 功能：blog8
 **/
@RestController
@RequestMapping("api/file")
public class FileApiController {

    private final FileService fileService;

    public FileApiController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 文件上传
     * @param fileUpload fileUpload
     * @return file
     */
    @PostMapping("upload")
    public ResponseEntity<List<FileInfo>> fileUpload(FileUpload fileUpload) {
        List<FileInfo> fileInfoList = fileService.fileUpload(fileUpload);
        return ResponseEntity.ok(fileInfoList);
    }


    @GetMapping("dirs")
    public ResponseEntity<List<FileInfo>> queryDirs() {
        List<FileInfo> fileInfos = fileService.queryDirs();
        return ResponseEntity.ok(fileInfos);
    }

}
