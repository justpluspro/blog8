package com.qwli7.blog.file;

import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.file.converter.*;
import com.qwli7.blog.file.vo.ResizeControlArgs;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author qwli7
 * 2021/3/2 8:37
 * 功能：FileService
 * 本地文件服务
 * <ul>
 *     <li>图片/视频文件的缩放以及获取文件信息</li>
 *     <li>文件/文件夹的拷贝和重命名</li>
 *     <li>文件的拷贝</li>
 *     <li>文件/文件夹的移动</li>
 *     <li>文件/文件夹的删除</li>
 *     <li>部分文件的编辑</li>
 *     <li>文件保护，私有</li>
 * </ul>
 *
 **/
@Conditional(value = FileCondition.class)
@Service
public class FileService implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private static final int MAX_NAME_LENGTH = 255;
    private static final Set<String> editableExts = new HashSet<>(Arrays.asList("js", "css", "json","txt", "xml", "html", "md"));
    private final Path uploadRootPath;
    private final Path uploadThumbPath;
    private final Semaphore semaphore;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ExecutorService executorService;
    private final FileProperties fileProperties;
    private Map<ConvertAction, AbstractMediaConverter> converters;

    public FileService(FileProperties fileProperties) throws IOException {
        this.fileProperties = fileProperties;
        this.uploadRootPath = Paths.get(fileProperties.getUploadPath());
        this.uploadThumbPath = fileProperties.getUploadThumbPath() == null ? null : Paths.get(fileProperties.getUploadThumbPath());
        FileUtil.forceMkdir(uploadRootPath);
        if(uploadThumbPath != null) {
            FileUtil.forceMkdir(uploadThumbPath);
        }
        semaphore = new Semaphore(fileProperties.getTotalSem());
        executorService = Executors.newFixedThreadPool(fileProperties.getTotalSem());
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
        lock.writeLock().lock();
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
                Files.copy(in, dest);
            } catch (FileAlreadyExistsException e){
                throw new LogicException("file.already.exists", "文件已经存在");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return getFileInfoDetail(dest);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private FileInfoDetail getFileInfoDetail(Path dest) {
        return null;
    }


    /**
     * 创建文件
     * @param fileCreate fileCreate
     */
    public void createFile(FileCreate fileCreate) {
        final Lock lock = this.lock.writeLock();
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
        final Lock lock = this.lock.writeLock();
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
     * 如果访问的是缩略图，则返回缩略图
     *      图片访问缩略图
     *      视频返回视频封面
     *
     * 如果访问的是原图，则直接返回原图
     * 如果访问的是视频，则视频可能会被缩放返回
     * @param requestPath 请求文件路径
     * @param supportWebp 是否支持 webp
     *                   除开 Safari 外，其他的浏览器都应该是支持 webp 的
     * @return Resource
     */
    public Optional<Resource> getProcessedFile(String requestPath, boolean supportWebp) {
        final ResizeResolver rr = new ResizeResolver(requestPath);
        final String sourcePath = rr.getSourcePath();
        final Resize resize = rr.getResize();
        if(!resize.isValid()) {
            // 无效的缩放属性
            return Optional.empty();
        }
        final Optional<Path> path = searchFile(sourcePath);
        if(!path.isPresent()) {
            return Optional.empty();
        }
        final Path file = path.get();
        if(uploadThumbPath == null) {
            return Optional.of(new ReadablePathResource(file));
        }

        if(resize.isKeepRate()) {
            //原样输出
            return Optional.of(new ReadablePathResource(file));
        }
        return getThumbnailFile(file, resize, supportWebp);
    }


    /**
     * 获取缩略图文件
     * @param resize resize
     * @param file file
     * @param supportWebp supportWebp
     * @return Optional
     */
    private Optional<Resource> getThumbnailFile(Path file, Resize resize, boolean supportWebp) {
        final Lookup lookup = Lookup.newLookup(file).regularFile(true).mustDir(false).mustExists(true);

        final Path thumbDir = getThumbDir(file);

        Path thumbFile;
        final Path thumbDirParent = thumbDir.getParent();
        final boolean forceResize = resize.isForceResize();
        final Integer height = resize.getHeight();
        final Integer width = resize.getWidth();

        String thumbFileSuffix;
        if(forceResize) {
            thumbFileSuffix = "_h" + height + "_w" + width + "_force";
        } else {
            if(width == null || width == 0) {
                thumbFileSuffix = "_h" + height;
            } else if(height == null || height == 0) {
                thumbFileSuffix = "_w" + width;
            } else {
                thumbFileSuffix = "_h" + height + "_w" + width;
            }
        }

        String filenameExtension = StringUtils.getFilenameExtension(file.getFileName().toString());
        boolean toWebp = supportWebp && (MediaUtils.isJPEG(filenameExtension) || MediaUtils.isPNG(filenameExtension));
        //缩略图不存在，处理下
        if(MediaUtils.canHandleImage(filenameExtension)) {
            thumbFile = Paths.get(thumbDirParent.toString(),  file.getFileName().toString().replace(".", "@")+ thumbFileSuffix+ "." + filenameExtension);
            if(Files.exists(thumbFile)) {
                return Optional.of(new ReadableThumbPathResource(thumbFile));
            }
            final AbstractMediaConverter mediaConverter = converters.get(ConvertAction.IMG2IMG);
            final ResizeControlArgs resizeControlArgs = new ResizeControlArgs();
            resizeControlArgs.setResize(resize);
            resizeControlArgs.setAction(ConvertAction.IMG2IMG.getAction());
            mediaConverter.convert(file.toFile(), thumbFile.toFile(), resizeControlArgs);
        }

        if(MediaUtils.canHandleVideo(filenameExtension)) {
            thumbFile = Paths.get(thumbDirParent.toString(),  file.getFileName().toString().replace(".", "@")+ thumbFileSuffix+ ".png");
            if(Files.exists(thumbFile)) {
                return Optional.of(new ReadableThumbPathResource(thumbFile));
            }
            try {
                semaphore.acquire();
                Path finalThumbFile = thumbFile;
                final Future<String> future = executorService.submit(() -> {
                    final AbstractMediaConverter mediaConverter = converters.get(ConvertAction.VIDEO2IMG);
                    ResizeControlArgs resizeControlArgs = new ResizeControlArgs();
                    resizeControlArgs.setResize(resize);
                    resizeControlArgs.setAction(ConvertAction.VIDEO2IMG.getAction());
                    mediaConverter.convert(file.toFile(), finalThumbFile.toFile(), resizeControlArgs);
                    return "success";
                });
                final String s = future.get(10, TimeUnit.SECONDS);
                if("success".equals(s)) {
                    return Optional.of(new ReadableThumbPathResource(thumbFile));
                }
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                ex.printStackTrace();
            }finally {
                semaphore.release();
            }

        }
        return Optional.of(new ReadablePathResource(file));
    }


    private Path getThumbDir(Path file) {
        return uploadThumbPath.resolve(uploadRootPath.relativize(file));
    }

    private Path getThumbPath(Path file, Resize resize, boolean toWebp) {
        Path thumbDir = getThumbDir(file);
        String name = resize.toString();
        return thumbDir.resolve(toWebp ? String.format("%s.WEBP", name) : name);
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


    private boolean isEditable(String ext) {
        return editableExts.stream().anyMatch(e -> e.equalsIgnoreCase(ext));
    }

    private void writeContent(Path path, String content) {
        if(!isEditable(FileUtil.getFileExtension(path))) {
            throw new LogicException("fileService.edit.unable", "该文件不可被编辑");
        }
        try {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    static class Lookup {
        private boolean regularFile;

        private boolean mustDir;

        private boolean mustExists;

        private Path file;

        private Lookup(Path file) {
            this.file = file;
        }

        public Lookup regularFile(boolean regularFile) {
            this.regularFile = regularFile;
            return this;
        }

        public Lookup mustDir(boolean mustDir) {
            this.mustDir = mustDir;
            return this;
        }

        public Lookup mustExists(boolean mustExists) {
            this.mustExists = mustExists;
            return this;
        }

        public static Lookup newLookup(Path path) {
            return new Lookup(path);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        converters = new HashMap<>();
        converters.put(ConvertAction.IMG2IMG, new Image2ImageConverter(fileProperties.getGraphicsMagickPath()));
        converters.put(ConvertAction.VIDEO2IMG,  new Video2ImageConverter(fileProperties.getFfmpegPath(), fileProperties.getGraphicsMagickPath()));
        converters.put(ConvertAction.WATERMARK, new WatermarkConverter(fileProperties.getFfmpegPath(), fileProperties.getGraphicsMagickPath()));
        converters.put(ConvertAction.VIDEO2VIDEO, new Video2VideoConverter(fileProperties.getFfmpegPath()));
        converters.put(ConvertAction.VIDEO2GIF, new Video2GifConverter(fileProperties.getFfmpegPath()));
        converters.put(ConvertAction.METADATA, new ExtractMetaConverter(fileProperties.getFfmpegPath(), fileProperties.getGraphicsMagickPath()));
    }
}
