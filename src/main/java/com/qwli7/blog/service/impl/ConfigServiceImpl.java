package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.entity.GlobalConfig;
import com.qwli7.blog.entity.vo.ConfigBean;
import com.qwli7.blog.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author qwli7
 * @date 2023/2/17 17:25
 * 功能：blog8
 **/
@Service
public class ConfigServiceImpl implements ConfigService {

    private final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    private final String configPath = System.getProperty("user.home");

    private static final String BLOG_INITIAL = "blog.initial";

    private static final String ADMIN_AVATAR = "blog.admin.avatar";

    @Override
    public GlobalConfig loadConfig() {
        GlobalConfig globalConfig = BlogContext.getGlobalConfig();
        if(globalConfig == null) {
            //加载本地文件配置
            Path config = Paths.get(configPath, ".blog_config");
            if(Files.notExists(config)) {
                return null;
            }
            Properties properties = new Properties();
            try {
                properties.load(Files.newInputStream(config));
            } catch (IOException e) {
                logger.error("loading config failed.", e);
            }

            //初始化属性
            Boolean initial = Boolean.valueOf(properties.getProperty(BLOG_INITIAL));


            globalConfig = new GlobalConfig();
            globalConfig.setInitial(initial);
            BlogContext.updateGlobalConfig(globalConfig);
        }

        return globalConfig;
    }

    @Override
    public void updateConfig(ConfigBean configBean) {
        String avatarUrl = configBean.getAvatarUrl();
        GlobalConfig globalConfig = loadConfig();
    }
}
