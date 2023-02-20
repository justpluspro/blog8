package com.qwli7.blog.plugin.file;

import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author qwli7
 * @date 2023/2/17 11:28
 * 功能：blog8
 **/
@Component
public class FileService implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    /**
     * 无效尺寸
     */
    private static final Resize INVALID_RESIZE = new Resize(-1, -1);
    /**
     * 默认尺寸
     */
    private static final Resize DEFAULT_SIZE = new Resize(0, 0);

    /**
     * 可处理的视频格式
     */
    private static final List<String> VIDEO_FORMAT_HANDLED = Arrays.asList(
            "mp4", "avi", "rmvb", "3gp"
    );

    /**
     * 可处理的图片格式
     */
    private static final List<String> IMAGE_FORMAT_HANDLED = Arrays.asList(
            "png", "jpeg", "webp", "gif", "jpg"
    );

    @Value("${blog.file.path}")
    private String blogFilePath;


    private Path rootPath;

    public List<FileInfo> fileUpload(FileUpload fileUpload) {

        Path uploadPath = getUploadPath(fileUpload.getPath());

        List<MultipartFile> files = fileUpload.getFiles();
        if(CollectionUtils.isEmpty(files)) {
            throw new BizException(Message.AUTH_FAILED);
        }

        List<FileInfo> fileInfos = new ArrayList<>();

        for(MultipartFile file: files) {
            FileInfo fileInfo = new FileInfo();
            String originalFilename = file.getOriginalFilename();
            Path uploadFilePath = null;
            try {
                uploadFilePath = Paths.get(uploadPath.toString(), originalFilename);
                Files.copy(file.getInputStream(), uploadFilePath);
            } catch (IOException e) {
                logger.error("file upload failed. ", e);
                continue;
            }

            fileInfo.setFilename(originalFilename);
            fileInfo.setPath(File.pathSeparator + uploadFilePath.subpath(uploadPath.getNameCount(), uploadFilePath.getNameCount()));

            String baseName = FileUtils.getBaseName(originalFilename);
            String fileExtension = FileUtils.getFileExtension(originalFilename);

            Optional<FileExtension> fileTypeOptional = FileExtension.convert(fileExtension);
            fileTypeOptional.ifPresent(fileInfo::setFileExtension);

            fileInfo.setBasename(baseName);
            fileInfo.setFileSize(file.getSize());

            fileInfos.add(fileInfo);

        }

        return fileInfos;
    }

    public Optional<Resource> getFileInfo(String s) {
        Resize resize = resolveResize(s);
        if(DEFAULT_SIZE.getWidth() == resize.getWidth() && DEFAULT_SIZE.getHeight() == resize.getHeight()) {
            Path filePath = Paths.get(blogFilePath, s);
            if(Files.notExists(filePath)) {
                return Optional.empty();
            }
            LocalOriginalFileResource localOriginalFileResource = new LocalOriginalFileResource(filePath);
            return Optional.of(localOriginalFileResource);
        }
        int width = resize.getWidth();
        int height = resize.getHeight();
        if(resize.isScaleRatio()) {

            if(width != 0) {
                //执行视频图片缩放


                return Optional.empty();
            }

            if(height != 0) {

                return Optional.empty();
            }
        }

        if(resize.isForceScale()) {
            // 是否强制缩放
            // 宽高都存在，
            if(width != 0 && height != 0) {

            } else {
                if(width != 0) {

                }

                if(height != 0) {


                }

                return Optional.empty();
            }
        }

//        LocalOriginalFileResource localOriginalFileResource = new LocalOriginalFileResource(filePath);

        return Optional.empty();
    }

    /**
     * 解析图片缩放尺寸
     * @param s s
     * @return Resize
     */
    private Resize resolveResize(String s) {
        String resizeStr = s.substring(s.lastIndexOf("/") + 1);
        if(StringUtils.isEmpty(resizeStr)) {
            //无缩放尺寸，说明访问路径以 / 结尾，这种不认为它是文件
            return INVALID_RESIZE;
        }
        //判断是否包含 .
        if(resizeStr.contains(".")) {
            // 表示应该返回原图
            //TODO 如果是视频或者图片类型的，就要做对应的压缩
            return new Resize(0, 0);
        }
        // 200 宽度缩放成 200 => 等比例缩放
        // x200 高度缩放成 200 => 等比例缩放
        // 200x200 宽度和高度均缩放成 200
        //200x200! 强制将宽度和高度缩放成 200
        // 200! 宽度强制缩放成 200
        // x200! 高度强制缩放成 200
        if(resizeStr.startsWith("x")){
            // x200 || x200!
        }



        return new Resize(0, 0);
    }


    private Path getUploadPath(String path) {
        Path rootPath = Paths.get(blogFilePath);
        Path uploadPath;
        if(StringUtils.isEmpty(path)) {
            uploadPath = rootPath;
        } else {
            uploadPath = Paths.get(rootPath.toString(), path);
        }
        return uploadPath;
    }


    private boolean videoCanEdit(String extension) {
        return VIDEO_FORMAT_HANDLED.contains(extension);
    }

    private boolean imageCanEdit(String extension) {
        return IMAGE_FORMAT_HANDLED.contains(extension);
    }

    public List<FileInfo> queryDirs() {
        Path rootPath = Paths.get(blogFilePath);
        List<FileInfo> fileInfos = new ArrayList<>();

        try {
            Files.walkFileTree(rootPath, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileType(FileType.DIR);
                    if(dir.equals(rootPath)) {
                        fileInfo.setPath(File.separator);
                    } else {
                        fileInfo.setPath(dir.toString().substring(rootPath.toString().length()));
                    }
                    fileInfos.add(fileInfo);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileInfos;
    }

    public List<FileInfo> getFileInfos(FileQueryParams fileQueryParams) {
        Path queryPath = getUploadPath(fileQueryParams.getQueryPath());
        int skip = (fileQueryParams.getPage()-1)*fileQueryParams.getSize();
        int limit = fileQueryParams.getSize();

        String query = fileQueryParams.getQuery();

        List<FileInfo> fileInfos = new ArrayList<>();

        Predicate<Path> predicate = new Predicate<Path>() {
            @Override
            public boolean test(Path path) {
                if(StringUtils.isEmpty(query)) {
                    return true;
                }
                if(FileUtils.getFullname(path).contains(query)) {
                    return true;
                }

                return false;
            }
        };

        try {
            fileInfos = Files.walk(queryPath, FileVisitOption.values()).filter(predicate).map(this::getFileInfo).skip(skip).limit(limit).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileInfos;
    }


    private FileInfo getFileInfo(Path file) {

        FileInfo fileInfo = new FileInfo();
        if (Files.isDirectory(file)) {
            fileInfo.setFileType(FileType.DIR);
            fileInfo.setFilename(file.getFileName().toString());
            fileInfo.setFileSize(0);
        } else {
            fileInfo.setFileType(FileType.FILE);
            fileInfo.setBasename(FileUtils.getBaseName(file));
            String fileExtension = FileUtils.getFileExtension(file);
            fileInfo.setFilename(file.getFileName().toString());
            FileExtension.convert(fileExtension).ifPresent(fileInfo::setFileExtension);
            try {
                fileInfo.setFileSize(Files.size(file));
            } catch (IOException e) {
                fileInfo.setFileSize(-1);
            }
        }
        if(file.equals(rootPath) && file.getNameCount() <= rootPath.getNameCount()) {
            //root dir
            fileInfo.setPath(File.separator);
            fileInfo.setBasename("");
            fileInfo.setFilename("");
        } else {
            fileInfo.setPath(File.separator + file.subpath(rootPath.getNameCount(), file.getNameCount()));
        }
        return fileInfo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.rootPath = Paths.get(blogFilePath);
    }
}
