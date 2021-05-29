package com.qwli7.blog.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 缩放尺寸解析
 * @author liqiwen
 * @since 2.0
 */
public class ResizeResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final static String CONCAT = "x";
    private final static String FORCE = "!";
    private final static Resize INVALID_RESIZE = new Resize(-1);

    private final Resize resize;

    private final String sourcePath;

    public ResizeResolver(String filePath) {
        this.resize = resolveResize(filePath);
        this.sourcePath = getResourcePathFromResize(filePath);
    }

    public Resize getResize() {
        return resize;
    }

    private String getResourcePathFromResize(String filePath) {
        final Resize resize = getResize();
        final boolean keepRate = resize.isKeepRate();
        if(keepRate) {
            return filePath;
        }
        return filePath.substring(0, filePath.lastIndexOf("/"));
    }


    public String getSourcePath() {
        return sourcePath;
    }


    /**
     * 解析缩放尺寸
     * 1. 600
     * 2. x600
     * 3. 600x
     * 4. 600x800
     * 5. 600x800!
     * @param filePath filePath
     * @return Resize
     */
    private Resize resolveResize(String filePath) {
        logger.info("method<resolveResize> filePath: [{}]", filePath);
        String resizeStrOrFilename = StringUtils.getFilename(filePath);
        logger.info("method<resolveResize> filePath: [{}]", resizeStrOrFilename);
        final String filenameExtension = StringUtils.getFilenameExtension(resizeStrOrFilename);
        if(!StringUtils.isEmpty(filenameExtension)) {
            return new Resize(0,0,true, false);
        }
        // no filenameExtension
        // 600x
        if(resizeStrOrFilename.endsWith(CONCAT)) {
            int width;
            try {
                width = Integer.parseInt(resizeStrOrFilename.substring(0, resizeStrOrFilename.lastIndexOf(CONCAT)));
                return new Resize(width, 0, false, false);
            } catch (NumberFormatException e) {
                return INVALID_RESIZE;
            }
        }
        // x600
        if(resizeStrOrFilename.startsWith(CONCAT)) {
            int height;
            try {
                height = Integer.parseInt(resizeStrOrFilename.substring(resizeStrOrFilename.lastIndexOf(CONCAT)));
                return new Resize(0, height, false, false);
            } catch (NumberFormatException e) {
                return INVALID_RESIZE;
            }
        }
        //600x800! || 600x800
        if(resizeStrOrFilename.contains(CONCAT)) {
            boolean forceResize = false;
            if(resizeStrOrFilename.endsWith(FORCE)) {
                resizeStrOrFilename = resizeStrOrFilename.substring(0, resizeStrOrFilename.lastIndexOf(FORCE));
                forceResize = true;
            }
            final String[] arr = resizeStrOrFilename.split(CONCAT);
            if(arr.length != 2) {
                return INVALID_RESIZE;
            }
            try {
                return new Resize(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                        false, forceResize);
            } catch (NumberFormatException e){
                return INVALID_RESIZE;
            }
        }
        // 只有一个数字
        try {
            return new Resize(Integer.parseInt(resizeStrOrFilename), Integer.parseInt(resizeStrOrFilename), false, false);
        } catch (NumberFormatException e){
            return INVALID_RESIZE;
        }

    }
}
