package com.dumphex.blog.file;

import com.dumphex.blog.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author qwli7 
 * 2021/3/17 15:32
 * 功能：FileResourceResolver
 **/
class FileResourceResolver implements ResourceResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final FileService fileService;

    public FileResourceResolver(FileService fileService) {
        this.fileService = fileService;
    }


    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations,
                                    ResourceResolverChain chain) {
        logger.info("method<resolveResource> static resource requestPath: [{}]", requestPath);
        if(request == null || StringUtils.isEmpty(requestPath)) {
            return null;
        }
        final String method = request.getMethod().toLowerCase();

        if(!Arrays.asList(HttpMethod.GET.name().toLowerCase(), HttpMethod.OPTIONS.name().toLowerCase()).contains(method)) {
            // get static resource if not 'get' or 'options'
            // will return null
            return null;
        }
        return fileService.getProcessedFile(requestPath, WebUtils.isSupportWebp(request)).orElse(null);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return null;
    }
}
