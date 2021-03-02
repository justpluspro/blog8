//package com.qwli7.blog.file;
//
//import com.qwli7.blog.exception.LogicException;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.FileAlreadyExistsException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
///**
// * @author qwli7
// * @date 2021/3/2 8:37
// * 功能：blog
// **/
//@Service
//public class FileService {
//
//    private final Path rootPath;
//    private final Path thumbPath;
//    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//
//    private final FileProperties fileProperties;
//
//    public FileService(FileProperties fileProperties) throws IOException {
//        this.fileProperties = fileProperties;
//        this.rootPath = Paths.get(fileProperties.getUploadPath());
//        this.thumbPath = Paths.get(fileProperties.getUploadThumbPath());
//        if(!rootPath.toFile().exists() && rootPath.toFile().isDirectory()) {
//            Files.createDirectories(rootPath);
//        }
//        if(!thumbPath.toFile().exists() && thumbPath.toFile().isDirectory()) {
//            Files.createDirectories(thumbPath);
//        }
//    }
//
//    public FileInfoDetail uploadFile(String dirPath, MultipartFile file) {
//        readWriteLock.writeLock().lock();
//        try {
//            if (StringUtils.startsWithIgnoreCase(dirPath, "/")) {
//                dirPath = dirPath.substring(1);
//            }
//            Path path;
//            if (!StringUtils.isEmpty(dirPath)) {
//                path = rootPath.resolve(dirPath);
//            } else {
//                path = rootPath;
//            }
//            createDirectories(path);
//
//            String fileName = file.getName();
//            //去除文件中的非法字符
//
//            Path dest = path.resolve(fileName);
//
//
//            try(InputStream in = new BufferedInputStream(file.getInputStream())) {
//                Files.copy(path, dest);
//            } catch (FileAlreadyExistsException ex) {
//                throw new LogicException("file.already.exists", "文件已经存在");
//            }
//
//            String fileExtension = FileUtil.getFileExtension(fileName);
//            if(MediaHandler.canHandle(fileExtension)) {
//
//
//            }
//
//            return getFileInfoDetail(dest);
//
//        } finally {
//            readWriteLock.writeLock().unlock();
//        }
//    }
//
//    private FileInfoDetail getFileInfoDetail(Path dest) {
//        return null;
//    }
//
//
//    private void createDirectories(Path path) throws IOException {
//        if(path == null) {
//            return;
//        }
//        if(path.toFile().exists()) {
//            return;
//        }
//        if(!path.toFile().isDirectory()) {
//            return;
//        }
//        Files.createDirectories(path);
//    }
//}
