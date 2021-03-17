package com.qwli7.blog.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qwli7 
 * @date 2021/3/17 15:32
 * 功能：blog
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
        logger.info("获取的文件 requestPath:[{}]", requestPath);
        if(request == null) {
            return null;
        }
        return fileService.processFile(requestPath);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return null;
    }
}
