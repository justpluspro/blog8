package com.dumphex.blog.file;

import java.nio.file.Path;

/**
 * 可读取的缩略图资源
 * @author liqiwen
 * @since 2.3
 */
class ReadableThumbPathResource extends ReadablePathResource {


    public ReadableThumbPathResource(Path path) {
        super(path);
    }
}
