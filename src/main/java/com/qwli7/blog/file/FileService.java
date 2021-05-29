package com.qwli7.blog.file;

import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.file.vo.VideoCutParams;
import com.qwli7.blog.util.SystemType;
import com.qwli7.blog.util.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
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
    private final Path uploadRootPath = Paths.get(System.getProperty("user.home"), "upload");

    /**
     * 缩略图存放路径
     */
    private final Path uploadThumbPath = Paths.get(System.getProperty("user.home"), "upload", "thumb");

    /**
     * 读写锁
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    private final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    /**
     * 文件属性
     */
    private final FileProperties fileProperties;

    public FileService(FileProperties fileProperties) {
        this.fileProperties = fileProperties;

        logger.info("method<FileService> uploadRootPath:[{}]", uploadRootPath);
        logger.info("method<FileService> uploadThumbPath:[{}]", uploadThumbPath);
        try {
            if (!uploadRootPath.toFile().exists()) {
                Files.createDirectories(uploadRootPath);
            }
            if (!uploadThumbPath.toFile().exists()) {
                Files.createDirectories(uploadThumbPath);
            }
        } catch (IOException ex) {
            throw new RuntimeException("本地文件服务已经开启，创建上传目录失败", ex);
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
     * @param files files
     * @return FileInfoDetail
     */
    public FileInfoDetail uploadFile(String dirPath, List<MultipartFile> files) {
        readWriteLock.writeLock().lock();
        try {
            Path path;
            if(StringUtils.isEmpty(dirPath) || StringUtils.pathEquals(dirPath, FileUtil.pathSeparator)) {
                path = uploadRootPath;
            } else {
                while (StringUtils.startsWithIgnoreCase(dirPath, FileUtil.pathSeparator)) {
                    dirPath = dirPath.substring(1);
                }
                path = uploadRootPath.resolve(dirPath);
            }

            logger.info("filePath:[{}]", path.toAbsolutePath());
            final Path parent = path.getParent();
            final boolean directories = FileUtil.createDirectories(parent);
            if(!directories) {
                throw new LogicException("path.notDirectories", "指定路径非文件夹");
            }


            for(MultipartFile file: files) {
                String fileName = file.getOriginalFilename();
                //去除文件中的非法字符
                if(StringUtils.isEmpty(fileName)) {
                    throw new LogicException("fileName.isEmpty", "文件名称不能为空");
                }
                Path dest = path.resolve(fileName);
                try (InputStream in = new BufferedInputStream(file.getInputStream())) {
                    Files.copy(in, dest);
                } catch (IOException ex) {
                    throw new LogicException("file.already.exists", "文件已经存在");
                }
            }
            //目标文件

//            return getFileInfoDetail(dest);
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
        final Lock lock = readWriteLock.writeLock();
        lock.lock();

        try {
            final String path = fileCreate.getPath();
            final String fileName = fileCreate.getFileName();
            final FileType fileType = fileCreate.getFileType();
            //组合成一个文件路径
            final Path filePath = Paths.get(path, fileName);
            //跟 root 合并成一个完整的路径
            final String cleanPath = StringUtils.cleanPath(filePath.toString());
            final Path fullPath = uploadRootPath.resolve(Paths.get(cleanPath));
            if(fileType == FileType.DIRECTORY) {
                if(fullPath.toFile().exists()) {
                    throw new LogicException("directories.exists", "文件夹已经存在");
                }
                FileUtil.createDirectories(fullPath);
            } else if(fileType == FileType.FILE) {
                if(fullPath.toFile().exists()) {
                    throw new LogicException("file.exists", "文件已经存在");
                }
                final String ext = FileUtil.getFileExtension(cleanPath);
                if(MediaConverter.isText(ext)) {
                    // 创建的文件必须都是可编辑的
                    throw new LogicException("file.cannot.created", "无法创建扩展名为" + ext + "的文件");
                }
                FileUtil.createFile(filePath);
            } else {
                // 不是文件也不是文件夹
                throw new LogicException("illegal.fileType", "非法的文件类型");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 更新文件
     * @param fileUpdated fileUpdated
     */
    public void updateFile(FileUpdated fileUpdated) {
        final Lock lock = readWriteLock.writeLock();
        lock.lock();
        try {
            final String path = fileUpdated.getPath();
            final Path fullPath = getFullPath(path);
            // 检验文件是否可编辑
            Files.write(fullPath, fileUpdated.getContent().getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            logger.error("写文件失败：[{}]", ex.getMessage(), ex);
            throw new LogicException("write.content.failed", "写文件失败");
        } finally {
            lock.unlock();
        }
    }

    private Path getFullPath(String path) {
        return uploadRootPath.resolve(Paths.get(path));
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


//    private FileInfoDetail getFileInfoDetail(Path dest) {
//        if(MediaConverter.isVideo(dest) && MediaConverter.canHandle(dest)) {
//            final VideoMetaInfo videoMetaInfo = MediaConverter.getVideoMetaInfo(dest.toFile());
//            VideoCutParams cutParams = new VideoCutParams();
//            cutParams.setInputFile(dest.toFile());
//            cutParams.setContinuous(false);
//            cutParams.setWidth(600);
//            cutParams.setHeight(450);
//            MediaConverter.cutVideoFrame(cutParams);
//            FileInfoDetail fileInfoDetail = new FileInfoDetail();
//            fileInfoDetail.setHeight(videoMetaInfo.getHeight());
//            fileInfoDetail.setWidth(videoMetaInfo.getWidth());
//            fileInfoDetail.setExt(videoMetaInfo.getExtension());
//            fileInfoDetail.setPath(dest.toFile().getAbsolutePath());
//            return fileInfoDetail;
//        }
//
//        return null;
//    }

    /**
     * 获取处理过后的文件
     * @param requestPath 请求文件路径
     * @param supportWebp 是否支持 webp
     *                   除开 Safari 外，其他的浏览器都应该是支持 webp 的
     * @return Resource
     */
    public Optional<Resource> processFile(String requestPath, boolean supportWebp) {

        // requestPath  video/test.mp4  video/test.mp4/900


        final ResizeResolver rr = new ResizeResolver(requestPath);
        final String sourcePath = rr.getSourcePath();
        final Resize resize = rr.getResize();
        if(!resize.isValid()) {
            // 无效的缩放属性
            return Optional.empty();
        }
        if(resize.isKeepRate()) {
            //原样输出
            Path file = Paths.get(uploadRootPath.toString(), sourcePath);
            return Optional.of(new ReadablePathResource(file));
        }
        final String filenameExtension = StringUtils.getFilenameExtension(sourcePath);
        if(MediaUtils.canHandleImage(filenameExtension)) {



        }
        if(MediaUtils.canHandleVideo(filenameExtension)) {

        }


        Path file = Paths.get(uploadRootPath.toString(), requestPath);

        return Optional.of(new ReadablePathResource(file));

//        // 判断是否有缩放属性
//        final Resize resize = resizeResolver.getResize();
//        // 判断源文件是否存在
//        Optional<Path> targetFile = searchFile(sourcePath);
//        if(!targetFile.isPresent()) {
//            return Optional.empty();
//        }
//
//        // 如果没有缩放属性
//        if(resize == null) {
//            // 直接返回源文件
//            return Optional.of(new ReadablePathResource(targetFile.get()));
//        }
//
//        // 如果缩放属性无效
////        if(resize.invalid()) {
////            return Optional.empty();
////        }
//
//        // 解析缩放属性，判断缩略图中是否含有该缩略图
//        final Path file = targetFile.get();
//
//        final String ext = StringUtils.getFilenameExtension(sourcePath);
//
//        if (resize == null || resize.isValid()) {
//            if(isProcessableImage(ext)) {
//                return Optional.empty();
//            }
//        } else {
//
//            return getThumbnailFile(resize, file);
//        }
//        if(isProcessableVideo(ext)) {
//            final Future<?> future = threadPoolTaskExecutor.submit(new Runnable() {
//                @Override
//                public void run() {
//
//
//                }
//            });
//
////            final Object o = future.get(10, TimeUnit.SECONDS);
//            return Optional.empty();
//        }
//        return Optional.of(new ReadablePathResource(file));
    }

    private boolean isProcessableVideo(String ext) {
        return false;
    }

    private boolean isProcessableImage(String ext) {
        return false;
    }

    private Optional<Resource> getThumbnailFile(Resize resize, Path file) {
        if(resize.isValid()) {
            return Optional.empty();
        }

        LinkedList<String> commands = new LinkedList<>();
        commands.add(file.toFile().getAbsolutePath());
        commands.add("-quality");
        commands.add("90%");
        commands.add("-resize");
        if(resize.getWidth() != null) {
            commands.add(resize.getWidth() + "");
        }
        if(resize.getHeight() != null) {
            commands.add("x" + resize.getHeight());
        }

        return null;
    }

    private Optional<Path> searchFile(String sourcePath) {
        final Path file = Paths.get(uploadRootPath.toString(), sourcePath);
        if(file.toFile().isDirectory() || !file.toFile().exists()) {
            return Optional.empty();
        }
        return Optional.of(file);
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
