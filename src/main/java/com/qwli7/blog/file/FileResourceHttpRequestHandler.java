package com.qwli7.blog.file;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * @author qwli7 
 * @date 2021/3/17 15:27
 * 功能：blog
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
