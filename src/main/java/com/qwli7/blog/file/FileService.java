package com.qwli7.blog.file;

import com.qwli7.blog.exception.LogicException;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author qwli7
 * @date 2021/3/2 8:37
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


    public FileInfoDetail uploadFile(String dirPath, MultipartFile file) {
        readWriteLock.writeLock().lock();
        try {
            Path path;
            if(StringUtils.isEmpty(dirPath) || StringUtils.pathEquals(dirPath, File.pathSeparator)) {
                path = rootPath;
            } else {
                while (StringUtils.startsWithIgnoreCase(dirPath, File.pathSeparator)) {
                    dirPath = dirPath.substring(1);
                }
                path = rootPath.resolve(dirPath);
            }

            logger.info("filePath:[{}]", path.toAbsolutePath());
            final Path parent = path.getParent();
            System.out.println(rootPath.compareTo(parent));
            try {
//                if (rootPath.compareTo(parent) != 0) {
                    createDirectories(parent);
//                }
            }catch (IOException ex){
                throw new LogicException("", "");
            }

//            createDirectories(path);

            String fileName = file.getOriginalFilename();
            //去除文件中的非法字符
            if(!StringUtils.isEmpty(fileName)) {

                Path dest = path.resolve(fileName);
                try(InputStream in = new BufferedInputStream(file.getInputStream())) {
                    Files.copy(path, dest);
                } catch (IOException ex) {
                    throw new LogicException("file.already.exists", "文件已经存在");
                }


//                try {
//                    if(!dest.toFile().exists()) {
//                        Files.createFile(dest);
//                        Files.copy(file.getInputStream(), dest);
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }

                return getFileInfoDetail(dest);
            }

//

//            String fileExtension = FileUtil.getFileExtension(fileName);
//            if(MediaHandler.canHandle(fileExtension)) {
//
//
//            }


            return null;


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





    private void createDirectories(Path path) throws IOException {
        if(path == null) {
            return;
        }
        if(path.toFile().exists()) {
            return;
        }
        if(!path.toFile().isDirectory()) {
            return;
        }
        Files.createDirectories(path);
    }



    public Resource processFile(String requestPath) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
