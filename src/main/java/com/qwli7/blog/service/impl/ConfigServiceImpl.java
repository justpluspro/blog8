package com.qwli7.blog.service.impl;

import com.qwli7.blog.CommentStrategy;
import com.qwli7.blog.entity.BlogConfig;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.exception.AuthenticatedException;
import com.qwli7.blog.exception.LoginFailException;
import com.qwli7.blog.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author qwli7
 * @date 2021/2/26 13:38
 * 功能：blog
 **/
@Service
public class ConfigServiceImpl implements ConfigService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home")).resolve("blog/config.properties");

    private static final String LOGIN_NAME = "login.name";
    private static final String LOGIN_PWD = "login.password";
    private static final String USER_EMAIL = "user.email";
    private static final String USER_NICKNAME = "user.nickname";
    private static final String USER_AVATAR = "user.avatar";

    private static final String COMMENT_STRATEGY = "comment.strategy";

    private static Properties PROPERTIES;

    private User user;

    private BlogConfig blogConfig;

    static {
        try {
            if(!Files.exists(CONFIG_PATH)) {
                Files.createFile(CONFIG_PATH);
            }
            PROPERTIES = PropertiesLoaderUtils.loadProperties(new EncodedResource(new FileSystemResource(CONFIG_PATH),
                    StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new RuntimeException("配置文件初始化失败");
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
    public CommentStrategy getCommentStrategy() {
        loadConfig();

        return CommentStrategy.EACH;
    }

    @Transactional(readOnly = true, rollbackFor = LoginFailException.class)
    @Override
    public boolean authenticate(String name, String password) {
        logger.info("method[authenticate] name:[{}], password:[{}]", name, password);
        loadConfig();
        final String loginName = this.blogConfig.getLoginName();
        final String configPassword = this.blogConfig.getPassword();
        if(StringUtils.isEmpty(loginName) ||
                StringUtils.isEmpty(configPassword)) {
            return false;
        }
        final String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        logger.info("encryptPassword:[{}]", encryptPassword);

        if(!loginName.equals(name) ||  !configPassword.equals(encryptPassword)) {
            throw new LoginFailException("login.fail", "登录失败");
        }

        return true;
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
        user.setAvatar(this.blogConfig.getAvatar());
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
        String avatar = PROPERTIES.getProperty(USER_AVATAR);
        String commentStrategy = PROPERTIES.getProperty(COMMENT_STRATEGY, CommentStrategy.NEVER.name());

        blogConfig = new BlogConfig();
        // 设置登录名称
        blogConfig.setLoginName(loginName);
        // 登录密码
        blogConfig.setPassword(loginPwd);
        // 别名
        blogConfig.setNickname(nickname);
        // 邮箱
        blogConfig.setEmail(userEmail);
        // avatar
        blogConfig.setAvatar(avatar);
        // 设置评论策略
        blogConfig.setCommentStrategy(Enum.valueOf(CommentStrategy.class, commentStrategy));
    }
}
