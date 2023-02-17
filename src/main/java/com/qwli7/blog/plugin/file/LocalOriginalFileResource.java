package com.qwli7.blog.plugin.file;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author qwli7
 * @date 2023/2/17 13:38
 * 功能：blog8
 **/
public class LocalOriginalFileResource implements Resource {

    private final Path filePath;

    public LocalOriginalFileResource(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean exists() {
        return Files.exists(filePath);
    }

    @Override
    public URL getURL() throws IOException {
        return filePath.toFile().toURI().toURL();
    }

    @Override
    public URI getURI() throws IOException {
        return filePath.toFile().toURI();
    }

    @Override
    public File getFile() throws IOException {
        return filePath.toFile();
    }

    @Override
    public long contentLength() throws IOException {
        return Files.size(filePath);
    }

    @Override
    public long lastModified() throws IOException {
        return Files.getLastModifiedTime(filePath).toMillis();
    }

    @Override
    public Resource createRelative(String s) throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return filePath.getFileName().toString();
    }

    @Override
    public String getDescription() {
        return filePath.getFileName().toString();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(filePath);
    }
}
