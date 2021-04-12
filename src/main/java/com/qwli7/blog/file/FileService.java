package com.qwli7.blog.file;

import com.qwli7.blog.exception.LogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author qwli7
 * 2021/3/2 8:37
 * 功能：FileService
 **/
@Conditional(value = FileCondition.class)
@Service
public class FileService implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 文件存放根路径
     */
    private final Path rootPath;

    /**
     * 缩略图存放路径
     */
    private final Path thumbPath;

    /**
     * 读写锁
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 文件属性
     */
    private final FileProperties fileProperties;

    public FileService(FileProperties fileProperties) throws IOException {
        this.fileProperties = fileProperties;

        this.rootPath = Paths.get(System.getProperty("user.dir") ,fileProperties.getUploadPath(), "/");
        this.thumbPath = Paths.get(System.getProperty("user.dir"), fileProperties.getUploadThumbPath(), "/");
        logger.info("rootPath:[{}]", rootPath);
        logger.info("thumbPath:[{}]", thumbPath);
        if(!rootPath.toFile().exists() && rootPath.toFile().isDirectory()) {
            Files.createDirectories(rootPath);
        }
        if(!thumbPath.toFile().exists() && thumbPath.toFile().isDirectory()) {
            Files.createDirectories(thumbPath);
        }
    }


    /**
     * 查询文件
     * @param queryParam queryParam
     * @return FilePageResult
     */
    @Transactional(readOnly = true)
    public FilePageResult queryFiles(FileQueryParam queryParam) {
        final String path = queryParam.getPath();


        return new FilePageResult();
    }

    /**
     * 上传文件
     * @param dirPath dirPath
     * @param file file
     * @return FileInfoDetail
     */
    public FileInfoDetail uploadFile(String dirPath, MultipartFile file) {
        readWriteLock.writeLock().lock();
        try {
            Path path;
            if(StringUtils.isEmpty(dirPath) || StringUtils.pathEquals(dirPath, FileUtil.pathSeparator)) {
                path = rootPath;
            } else {
                while (StringUtils.startsWithIgnoreCase(dirPath, FileUtil.pathSeparator)) {
                    dirPath = dirPath.substring(1);
                }
                path = rootPath.resolve(dirPath);
            }

            logger.info("filePath:[{}]", path.toAbsolutePath());
            final Path parent = path.getParent();
            final boolean directories = FileUtil.createDirectories(parent);
            if(!directories) {
                throw new LogicException("path.notDirectories", "指定路径非文件夹");
            }

            String fileName = file.getOriginalFilename();
            //去除文件中的非法字符
            if(StringUtils.isEmpty(fileName)) {
                throw new LogicException("fileName.isEmpty", "文件名称不能为空");
            }

            Path dest = path.resolve(fileName);
            try (InputStream in = new BufferedInputStream(file.getInputStream())) {
                Files.copy(path, dest);
            } catch (IOException ex) {
                throw new LogicException("file.already.exists", "文件已经存在");
            }

            return getFileInfoDetail(dest);

        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 创建文件
     * @param fileCreate fileCreate
     */
    public void createFile(FileCreate fileCreate) {


    }

    /**
     * /1/2/3   2/3
     * @param start
     * @param end
     * @return
     */
    private List<Path> getPaths(Paths start, Paths end) {
        if(start.equals(end)) {
            return new ArrayList<>();
        }

        return null;
    }


    private FileInfoDetail getFileInfoDetail(Path dest) {
        return null;
    }


    public Resource processFile(String requestPath) {
        return null;
    }


    /**
     * /1/2/3/4/5
     * /1/2/3
     *
     * => /1/2/3/   /1/2/3/4   /1/2/3/4/5
     *
     * 获取两个路径中的子路径
     * @param start start
     * @param end end
     * @return List
     */
    public List<Path> getPathsBetweenTwoPath(Path start, Path end) {

        return new ArrayList<>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
