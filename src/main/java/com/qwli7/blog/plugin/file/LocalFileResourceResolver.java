package com.qwli7.blog.plugin.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qwli7
 * @date 2023/2/17 13:36
 * 功能：blog8
 **/
@Component
public class LocalFileResourceResolver implements ResourceResolver {

    private final FileService fileService;

    public LocalFileResourceResolver(FileService fileService) {
        this.fileService = fileService;
    }

    private final Logger logger = LoggerFactory.getLogger(LocalFileResourceResolver.class);
    @Override
    public Resource resolveResource(HttpServletRequest httpServletRequest, String s, List<? extends Resource> list,
                                    ResourceResolverChain resourceResolverChain) {

        logger.info("access file path. {}", s);

        return fileService.getFileInfo(s).orElse(null);
    }


    @Override
    public String resolveUrlPath(String s, List<? extends Resource> list, ResourceResolverChain resourceResolverChain) {
        return null;
    }
}
