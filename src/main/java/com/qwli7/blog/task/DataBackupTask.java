package com.qwli7.blog.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 数据备份定时器
 * @author liqiwen
 * @since 1.2
 * @version 1.2
 */
@ConditionalOnProperty(prefix = "blog.core", value = "back-up", havingValue = "true")
@PropertySource(value = "classpath:blog.properties")
@Component
public class DataBackupTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Scheduled(cron = "11 5 22 * * ?")
    public void dataBackup() {
        logger.info("method<dataBackup> started!");

    }
}
