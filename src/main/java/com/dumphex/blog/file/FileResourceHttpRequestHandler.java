package com.dumphex.blog.file;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Collections;

/**
 * @author qwli7 
 * 2021/3/17 15:27
 * 功能：FileResourceHttpRequestHandler
 **/
class FileResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    public FileResourceHttpRequestHandler(FileResourceResolver fileResourceResolver) {
        super();
    }

    @Override
    protected MediaType getMediaType(HttpServletRequest request,
                                     Resource resource) {
        if(resource instanceof FilePathResource) {
            return ((FilePathResource) resource).getMediaType();
        }
        return super.getMediaType(request, resource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // handle LOG WARN Locations list is empty.
        // No resources will be served unless a custom ResourceResolver is configured as an alternative to PathResourceResolver.
        this.getLocations().add(null);
        super.afterPropertiesSet();
    }
}
