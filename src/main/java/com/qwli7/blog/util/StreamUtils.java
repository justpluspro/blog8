package com.qwli7.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

/**
 * 流工具处理类
 * @author liqiwen
 * @since 2.0
 */
public class StreamUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private StreamUtils() {
        super();
    }

    /**
     * Resource To String
     * @param resource resource
     * @return String
     */
    public static String readResourceToString(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return org.springframework.util.StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * FileToString
     * @param file file
     * @return String
     */
    public static Optional<String> readFileToString(File file) {
        if(!file.exists() || file.isDirectory()) {
            return Optional.empty();
        }
        try {
            return Optional.of(StringUtils.collectionToDelimitedString(
                    Files.readAllLines(file.toPath(), Charset.defaultCharset()), "\r\n"));
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return Optional.empty();
    }
    
    
    public static StringBuffer readInputStream(InputStream inputStream) {
        StringBuffer stringBuffer = new StringBuffer();
        if(inputStream == null) {
            return stringBuffer;
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer;
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return stringBuffer;
    }
}
