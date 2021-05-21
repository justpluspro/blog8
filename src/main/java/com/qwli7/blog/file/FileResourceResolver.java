package com.qwli7.blog.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qwli7 
 * 2021/3/17 15:32
 * 功能：FileResourceResolver
 **/
public class FileResourceResolver implements ResourceResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final FileService fileService;

    public FileResourceResolver(FileService fileService) {
        this.fileService = fileService;
    }


    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations,
                                    ResourceResolverChain chain) {
        logger.info("method<resolveResource> requestPath:[{}]", requestPath);
        if(request == null) {
            return null;
        }
        final String method = request.getMethod();
        if(!HttpMethod.GET.name().equals(method) && !HttpMethod.OPTIONS.name().equals(method)) {
            return null;
        }
        return fileService.processFile(requestPath).orElse(null);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return null;
    }
}
