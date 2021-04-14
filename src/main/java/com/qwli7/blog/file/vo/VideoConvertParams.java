package com.qwli7.blog.file.vo;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 转换参数设置
 * @author liqiwen
 * @since 2.0
 */
public class VideoConvertParams implements Serializable {

    private final String[] presetArrs = {"ultrafast", "superfast", "veryfast", "faster", "fast",
            "medium", "slow", "slower", "veryslow", "placebo"};



    /**
     * 源文件
     */
    private File inputFile;

    /**
     * 转换视频是否带音频
     */
    private boolean withAudio;

    /**
     * crf 值，该值越高视频质量越高，建议取值 0~51
     * crf 视频的质量，值越小视频质量越高（取值为 0-51，直接影响视频码率大小），取值可参考 CrfValueEnum.code
     * 其中0为无损模式，数值越大，画质越差，生成的文件却越小, 一般认为 18 对从人眼上来说，是没有太大的影响，但是从技术角度上来说，还是有些影响的
     */
    private Integer crf;

    /**
     * 预设参数
     * 指定视频的编码速率（速率越快压缩率越低），取值参考 PresetValueEnum.presetValue
     * ffmpeg -i /Users/liqiwen/Desktop/ironman.mp4 -c:v libx264 -preset -tune /Users/liqiwen/Desktop/duck.mp4
     * 可以看到推荐的 presetValue 值有如下几种：
     *  ultrafast superfast veryfast faster fast medium slow slower veryslow placebo
     *
     *  -preset指定的编码速度越慢，获得的压缩效率就越高
     *
     *  这里默认取值为 veryslow，
     *  问题：
     *  既然不在乎等待时间，为什么不给-preset指定一个最慢的placebo呢？
     *
     *  原因：与 veryslow 相比，placebo 以极高的编码时间为代价，只换取了大概 1% 的视频质量提升。
     *  这是一种收益递减准则：
     *      slow 与 medium 相比提升了 5%~10%；
     *      slower 与 slow 相比提升了5%；
     *      veryslow 与 slower 相比提升了3%。
     */
    private String preset;

    /**
     * 转换宽度
     * 如果为空，则和原视频款度一致
     */
    private Integer width;

    /**
     * 目标视频高度
     * 如果为空，则和原视频高度一致
     */
    private Integer height;

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public boolean isWithAudio() {
        return withAudio;
    }

    public void setWithAudio(boolean withAudio) {
        this.withAudio = withAudio;
    }

    public Integer getCrf() {
        return crf;
    }

    public void setCrf(Integer crf) {
        this.crf = crf;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean matchPreset(String preset) {
        if(StringUtils.isEmpty(preset)) {
            return false;
        }
        return Arrays.asList(presetArrs).contains(preset);
    }
}
