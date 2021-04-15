package com.qwli7.blog.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * Mapper 工具类
 * @author liqiwen
 * @since 1.5
 */
public class MapperUtils {

    private final static Logger logger = LoggerFactory.getLogger(MapperUtils.class.getName());

    private static ObjectMapper objectMapper = new ObjectMapper();

    private MapperUtils() {
        super();
    }

    static {

        // 忽略空 bean 转 json 的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略 json 串中存在属性，但是对象中不存在属性的问题，避免报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    private static <T> T obj2Bean(String jsonStr, Class<T> clazz) {
        Assert.notNull(jsonStr, "待格式化的字符串不能为空");
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            logger.error("method<obj2Bean> occurred exception:[{}]", e.getMessage(), e);
        }
        return null;
    }

}
