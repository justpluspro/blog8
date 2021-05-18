package com.qwli7.blog.file;

import org.springframework.util.StringUtils;

/**
 * 缩放尺寸解析
 * @author liqiwen
 * @since 2.0
 */
public class ResizeResolver {

    private final static String CONCAT = "x";
    private final static String FORCE = "!";
    private final static Resize INVALID_RESIZE = new Resize();


    private final Resize resize;

    private final String sourcePath;

    public ResizeResolver(String filePath) {
        this.resize = resolveResize(filePath);

        this.sourcePath = getResourcePathFromResize();
    }

    private String getResourcePathFromResize() {
        return null;
    }


    private Resize resolveResize(String filePath) {
        final String filename = StringUtils.getFilename(filePath);


        return new Resize();
    }
}
