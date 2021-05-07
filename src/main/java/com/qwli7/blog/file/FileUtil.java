package com.qwli7.blog.file;


import com.qwli7.blog.exception.LogicException;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author qwli7
 * 2021/3/2 8:54
 * 功能：FileUtil 文件工具类
 **/
public class FileUtil {


    public static final String pathSeparator = "/";

    private FileUtil() {
        super();
    }

    /**
     * 获取文件后缀名
     * @param fileName fileName
     * @return String
     */
    public static String getFileExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }

    /**
     * 获取文件后缀名
     * @param path path
     * @return String
     */
    public static String getFileExtension(Path path) {
        if(path.toFile().isDirectory()) {
            return "";
        }
        return getFileExtension(path.getFileName().toString());
    }

    /**
     * 创建文件夹
     * @param path path
     * @return boolean
     */
    public static boolean createDirectories(Path path) {
        if(path.toFile().isDirectory()) {
            try {
                Files.createDirectories(path);
            } catch (IOException ex){
                throw new LogicException("dir.createFailed", "文件夹创建失败");
            }
            return true;
        }
        return false;
    }

    /**
     * 创建文件
     * @param file file
     * @return boolean
     */
    public static boolean createFile(Path file) {
        if(file == null) {
            return false;
        }
        if (file.toFile().isDirectory()) {
            return false;
        }
        if(file.toFile().exists()) {
            return false;
        }
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new LogicException("file.createFailed", "文件创建失败");
        }
        return true;
    }

    /**
     * 删除文件
     * @param path path
     * @return true | false
     */
    public static boolean deleteFile(Path path) {
        if(path == null) {
            return false;
        }
        return false;
    }

    /**
     * 获取目录的大小
     * @param dir dir
     * @return long
     */
    public static long getDirectorySizeLegacy(File dir) {
        long length = 0;
        final File[] files = dir.listFiles();
        if(files != null) {
            for(File file: files) {
                if(file.isFile()) {
                    length += file.length();
                } else {
                    length += getDirectorySizeLegacy(file);
                }
            }
        }
        return length;
    }

    /**
     * 获取目录的大小，使用 java8
     * @param path path
     * @return long
     */
    public static long getDirectorySizeJava8(Path path) {
        long size = 0;
        try(Stream<Path> walk = Files.walk(path)) {
            size = walk.filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e){

                            return 0L;
                        }
                    }).sum();
        } catch (IOException e) {

        }
        return size;
    }

    /**
     * 获取目录的大小，使用 java7
     * @param path path
     * @return long
     */
    public static long getDirectorySizeJava7(Path path) {
        AtomicLong size = new AtomicLong(0);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    size.addAndGet(attrs.size());

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e){

        }
        return size.get();
    }

    public static List<String> findByExt(Path path, String ext) {
        List<String> result = new ArrayList<>();
        try(Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.endsWith(ext))
                    .collect(Collectors.toList());
        }catch (IOException ex){

        }
        return result;
    }


    public static void writeFile(String path, String content) {
        writeFile(Paths.get(path), content);
    }

    public static void writeFile(Path path, String content) {
        try {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
