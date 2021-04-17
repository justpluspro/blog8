package com.qwli7.blog.file;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;

/**
 * @author qwli7 
 * 2021/3/17 15:27
 * 功能：FileResourceHttpRequestHandler
 **/
public class FileResourceHttpRequestHandler extends ResourceHttpRequestHandler {


    public FileResourceHttpRequestHandler(FileResourceResolver fileResourceResolver, ResourceProperties resourceProperties) {
        super();
        this.setResourceResolvers(Collections.singletonList(fileResourceResolver));
        Duration period = resourceProperties.getCache().getPeriod();
        if(period != null) {
            this.setCacheSeconds((int) period.getSeconds());
        }
        CacheControl cacheControl = resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
        this.setCacheControl(cacheControl);
    }


    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        return super.getResource(request);
    }

    @Override
    protected MediaType getMediaType(HttpServletRequest request, Resource resource) {
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
