package com.qwli7.blog.file;

import com.qwli7.blog.plugin.file.FileInfo;
import com.qwli7.blog.plugin.file.FileType;
import com.qwli7.blog.plugin.file.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qwli7
 * @date 2023/2/20 13:37
 * 功能：blog8
 **/
public class FileTest {


    @Test
    public void test_file_walk() throws IOException {
        Path path = Paths.get("E:\\upload\\");
        List<FileInfo> fileInfos = new ArrayList<>();
        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileType(FileType.DIR);
                if(dir.equals(path)) {
                    fileInfo.setPath(File.separator);
                } else {
                    fileInfo.setPath(dir.toString().substring(path.toString().length()));
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
        for(FileInfo fileInfo: fileInfos) {

            System.out.println(fileInfo);

        }
    }

    @Test
    public void test_subpath() {
        Path root = Paths.get("/Users/liqiwen/workspace/upload");
        Path end = Paths.get("/Users/liqiwen/workspace/upload/a.png");
        System.out.println(end.subpath(root.getNameCount(), end.getNameCount()));
        Path end2 = Paths.get("/Users/liqiwen/workspace/upload");
        System.out.println(end.subpath(root.getNameCount(), end2.getNameCount()));



    }

    @Test
    public void test_get_basename() {
        String filepath = "/Users/liqiwen/workspace/upload/a.png";
        String baseName = FileUtils.getBaseName(filepath);
        String baseName1 = FileUtils.getBaseName(Paths.get(filepath));
        String fileExtension = FileUtils.getFileExtension(filepath);
        String fileExtension1 = FileUtils.getFileExtension(Paths.get(filepath));
        System.out.println(baseName1);
        System.out.println(baseName);
        System.out.println(fileExtension1);
        System.out.println(fileExtension);
    }
}
