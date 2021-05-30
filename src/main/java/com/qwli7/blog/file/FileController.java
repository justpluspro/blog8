package com.qwli7.blog.file;

import com.qwli7.blog.security.Authenticated;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author qwli7 
 * 2021/3/2 8:42
 * 功能：FileController
 **/
@Authenticated
@RestController
@Conditional(FileCondition.class)
@RequestMapping("api")
public class FileController {

    /**
     * 文件 fileService
     */
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 上传文件
     * @param fileUploadModel fileUploadModel
     * @return ResponseEntity
     */
    @PostMapping(value = "file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(FileUploadModel fileUploadModel) {
        final MultipartFile file = fileUploadModel.getFile();
        final String path = fileUploadModel.getPath();
        FileInfoDetail fileInfoDetail = fileService.uploadFile(path, file);
        return ResponseEntity.ok(fileInfoDetail);
    }

    /**
     * 创建文件
     * @param fileCreate fileCreate
     * @return ResponseEntity
     */
    @PostMapping(value = "file/create")
    public ResponseEntity<?> createFile(FileCreate fileCreate) {
        fileService.createFile(fileCreate);
        return ResponseEntity.noContent().build();
    }

    /**
     * 上传文件
     * @param queryParam queryParam
     * @return FilePageResult
     */
    @GetMapping(value = "files")
    public FilePageResult queryFiles(FileQueryParam queryParam) {

        return fileService.queryFiles(queryParam);
    }


}
