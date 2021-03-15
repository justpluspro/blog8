package com.qwli7.blog.file;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qwli7 
 * @date 2021/3/2 8:42
 * 功能：blog
 **/
@RestController
@RequestMapping("api")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping(value = "file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(FileUploadModel fileUploadModel) {
        final MultipartFile file = fileUploadModel.getFile();
        final String path = fileUploadModel.getPath();
        FileInfoDetail fileInfoDetail = fileService.uploadFile(path, file);
        return ResponseEntity.ok(fileInfoDetail);
    }


    @GetMapping("files")
    public Object queryFiles() {

        return new Object();
    }

}
