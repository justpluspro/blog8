package com.qwli7.blog.file;

import com.qwli7.blog.file.converter.AbstractMediaConverter;
import com.qwli7.blog.file.converter.Image2ImageConverter;
import com.qwli7.blog.file.converter.Video2ImageConverter;
import com.qwli7.blog.file.vo.ControlArgs;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;

import java.nio.file.Path;
import java.util.*;

/**
 * @author qwli7 
 * 2021/3/2 9:05
 * 功能：MediaProcessor
 **/
public class MediaProcessor {

    private final Map<String, AbstractMediaConverter> converters;

    private final FileProperties fileProperties;

    public MediaProcessor(FileProperties fileProperties) {
        super();
        this.fileProperties = fileProperties;
        this.converters = new HashMap<>();
        converters.put("img2img", new Image2ImageConverter(fileProperties.getGraphicsMagickPath()));
        converters.put("video2img", new Video2ImageConverter(fileProperties.getFfmpegPath()));
    }

    /**
     * 图片扩展名
     */
    public static final List<String> extensions = Arrays.asList("img", "mp4");

    /**
     * 文件是否能被处理
     * 图片：
     * 1. 裁剪
     * 2. 水印
     * 3. 缩放
     * 4. 去 exif
     *
     * 视频：
     * 1. 格式转换
     * 2. 压缩
     * 3. 截图
     * 4. 去 exif
     */
    public static boolean canHandle(String ext) {
        return extensions.contains(ext);
    }

    /**
     * gm convert -resize '200x200' -strip -quality 80% input.jpg output.jpg
     * @param resize resize
     * @param file file
     */
    public void resizeImage(Resize resize, Path file, Path output) {
        final String graphicsMagickPath = fileProperties.getGraphicsMagickPath();
        List<String> commands = new ArrayList<>();
        commands.add(graphicsMagickPath);
        commands.add("-convert");
        commands.add("-resize");
        if(resize.isForceResize()) {
            commands.add(resize.getWidth() + "x" + resize.getHeight() + "!");
        } else {
            if(resize.getWidth() != null && resize.getWidth() != 0) {
                commands.add(resize.getWidth() + "x");
            }
            if(resize.getHeight() != null && resize.getHeight() > 0) {
                commands.add("x"+ resize.getHeight());
            }
//            commands.add(resize.getWidth())
        }
        commands.add("-strip");
        commands.add("-quality");
        commands.add(resize.getQuality() + "");
        commands.add(file.toFile().getAbsolutePath());
        commands.add(output.toFile().getAbsolutePath());

        final AbstractMediaConverter mediaConverter = converters.get("img2img");
        final ControlArgs controlArgs = new ControlArgs();
        controlArgs.setAction("img2img");
        mediaConverter.convert(file.toFile(), output.toFile(), new ControlArgs());
    }
}
