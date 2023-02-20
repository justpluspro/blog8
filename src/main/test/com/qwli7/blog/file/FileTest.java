package com.qwli7.blog.file;

import com.qwli7.blog.plugin.file.FileInfo;
import com.qwli7.blog.plugin.file.FileType;
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
}
