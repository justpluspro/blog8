package com.qwli7.blog.file;


import com.qwli7.blog.exception.LogicException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author qwli7
 * 2021/3/2 8:54
 * 功能：FileUtil 文件工具类
 **/
public class FileUtil {

    private FileUtil() {
        super();
    }

    /**
     * 获取文件后缀名
     * @param fileName fileName
     * @return String
     */
    public static String getFileExtension(String fileName) {
        if(StringUtils.isEmpty(fileName)) {
            return "";
        }
        if(fileName.lastIndexOf(".") != -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1);
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
        return getFileExtension(path.getFileName().getFileName().toString());
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
     * @return
     */
    public static boolean deleteFile(Path path) {

        return false;
    }


    public static void main(String[] args) {

        System.out.println(System.getProperty("user.dir"));
    }
}
