package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.BlogConfig;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.service.ConfigService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2021/2/26 13:38
 * 功能：blog
 **/
@Service
public class BlogConfigServiceImpl implements ConfigService {

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home")).resolve("blog/config.properties");

    private static final String LOGIN_NAME = "login.name";
    private static final String LOGIN_PWD = "login.password";
    private static final String USER_EMAIL = "user.email";
    private static final String USER_NICKNAME = "user.nickname";

    private static Properties PROPERTIES;

    private User user;

    private BlogConfig blogConfig;

    static {
        try {
            if(!Files.exists(CONFIG_PATH)) {
                Files.createFile(CONFIG_PATH);
            }
            PROPERTIES = PropertiesLoaderUtils.loadProperties(new EncodedResource(new FileSystemResource(CONFIG_PATH), StandardCharsets.UTF_8));
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public BlogConfig getConfig() {
        loadConfig();
        BlogConfig config = new BlogConfig(this.blogConfig);
        config.setPassword(null);
        return config;
    }


    @Override
    public boolean authenticate(String name, String password) {
        loadConfig();
        if(StringUtils.isEmpty(this.blogConfig.getLoginName()) &&  StringUtils.isEmpty(this.blogConfig.getPassword())) {
            return false;
        }
        return this.blogConfig.getLoginName().equals(name) && this.blogConfig.getLoginName().equals(password);
    }


    @Override
    public void updatePassword(String password, String oldPassword) {

    }


    public synchronized void save(BlogConfig blogConfig) {

    }

    @Override
    public User getUser() {
        loadConfig();
        User user = new User();
        user.setUsername(this.blogConfig.getLoginName());
        user.setEmail(this.blogConfig.getEmail());
        return user;
    }


    private void loadConfig() {
        if(user == null) {
            synchronized (this) {
                if(user == null) {
                    loadConfigFromLocalFile();
                }
            }
        }
    }

    private void loadConfigFromLocalFile() {
        String loginName = PROPERTIES.getProperty(LOGIN_NAME);
        String loginPwd = PROPERTIES.getProperty(LOGIN_PWD);
        String userEmail = PROPERTIES.getProperty(USER_EMAIL);
        String nickname = PROPERTIES.getProperty(USER_NICKNAME);

        blogConfig = new BlogConfig();
        blogConfig.setLoginName(loginName);
        blogConfig.setPassword(loginPwd);
        blogConfig.setNickname(nickname);
        blogConfig.setEmail(userEmail);
    }
}
