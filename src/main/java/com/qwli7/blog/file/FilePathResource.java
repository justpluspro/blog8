package com.qwli7.blog.file;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public interface FilePathResource extends Resource {

    MediaType getMediaType();
}
