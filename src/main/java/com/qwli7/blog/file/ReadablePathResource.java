package com.qwli7.blog.file;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * 可读取的文件资源
 * @author liqiwen
 * @since 2.3
 */
class ReadablePathResource implements FilePathResource {

    private static final String WEBP = "webp";


    private final Path path;

    public ReadablePathResource(Path path) {
        this.path = path;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public URL getURL() throws IOException {
        return null;
    }

    @Override
    public URI getURI() throws IOException {
        return null;
    }

    @Override
    public File getFile() throws IOException {
        return path.toFile();
    }

    @Override
    public long contentLength() throws IOException {
        return path.toFile().length();
    }

    @Override
    public long lastModified() throws IOException {
        return -1;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return StringUtils.getFilename(path.getFileName().toString());
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public MediaType getMediaType() {
        final Path fileName = path.getFileName();
        final String extension = StringUtils.getFilenameExtension(fileName.toString());
        if(Arrays.asList("jpg", "jpeg").contains(extension)) {
            return MediaType.IMAGE_JPEG;
        }
        if("png".equals(extension)) {
            return MediaType.IMAGE_PNG;
        }
        if("webp".equals(extension)) {
            return MediaType.valueOf("image/webp");
        }
        if("mp4".equals(extension)) {
            return MediaType.valueOf("video/mp4");
        }
        if("gif".equals(extension)) {
            return MediaType.IMAGE_GIF;
        }
        return MediaType.ALL;
    }
}
