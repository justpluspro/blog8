package com.qwli7.blog.service.impl;

import com.qwli7.blog.CommentStrategy;
import com.qwli7.blog.Constant;
import com.qwli7.blog.entity.BlogConfig;
import com.qwli7.blog.entity.User;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.exception.LoginFailException;
import com.qwli7.blog.file.FileUtil;
import com.qwli7.blog.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author qwli7
 * @date 2021/2/26 13:38
 * 功能：系统设置
 **/
@Service
public class ConfigServiceImpl implements ConfigService, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home")).resolve("blog/config.properties");

    private static final String LOGIN_NAME = "login.name";
    private static final String LOGIN_PWD = "login.password";
    private static final String USER_EMAIL = "user.email";
    private static final String USER_NICKNAME = "user.nickname";
    private static final String USER_AVATAR = "user.avatar";
    private static final String COMMENT_STRATEGY = "comment.strategy";

    private static Properties properties;

    private User user;

    private BlogConfig blogConfig;


    @Override
    public BlogConfig getConfig() {
        loadConfig();
        blogConfig.setPassword(null);
        return blogConfig;
    }


    @Override
    public CommentStrategy getCommentStrategy() {
        loadConfig();
        return blogConfig.getCommentStrategy();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean authenticate(String name, String password) {
        loadConfig();
        final String loginName = this.blogConfig.getLoginName();
        final String configPassword = this.blogConfig.getPassword();
        if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(configPassword)) {
            return false;
        }
        final String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if(!loginName.equals(name) || !configPassword.equals(encryptPassword)) {
            throw new LoginFailException("login.failed", "登录失败");
        }
        return true;
    }

    /**
     * 更新用户密码
     * @param password password
     * @param oldPassword oldPassword
     */
    @Override
    public void updatePassword(String password, String oldPassword) {
        loadConfig();
        blogConfig.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        save(blogConfig);
    }

    /**
     * 保存配置文件
     */
    public synchronized void save(BlogConfig blogConfig) {
        try {
            loadConfig();
            final String avatar = blogConfig.getAvatar();
            if (!StringUtils.isEmpty(avatar) && !avatar.equals(properties.getProperty(USER_AVATAR))) {
                properties.setProperty(USER_AVATAR, avatar);
            }
            final String email = blogConfig.getEmail();
            if (!StringUtils.isEmpty(email) && !email.equals(properties.getProperty(USER_EMAIL))) {
                properties.setProperty(USER_EMAIL, email);
            }
            final String loginName = blogConfig.getLoginName();
            if (!StringUtils.isEmpty(loginName) && !loginName.equals(properties.getProperty(LOGIN_NAME))) {
                properties.setProperty(LOGIN_NAME, loginName);
            }
            if(!StringUtils.isEmpty(blogConfig.getPassword())) {
                final String password = DigestUtils.md5DigestAsHex(blogConfig.getPassword().getBytes(StandardCharsets.UTF_8));
                final String oldPassword = properties.getProperty(LOGIN_PWD);
                if (!StringUtils.isEmpty(password) && !password.equals(oldPassword)) {
                    properties.setProperty(LOGIN_PWD, password);
                }
            }
            final CommentStrategy commentStrategy = blogConfig.getCommentStrategy();
            if(commentStrategy != null) {
                final String commentStrategyName = commentStrategy.name();
                if (!StringUtils.isEmpty(commentStrategyName) && !commentStrategyName.equals(properties.getProperty(COMMENT_STRATEGY))) {
                    properties.setProperty(COMMENT_STRATEGY, commentStrategyName);
                }
            }

            final String nickname = blogConfig.getNickname();
            if (!StringUtils.isEmpty(nickname) && !nickname.equals(properties.getProperty(USER_NICKNAME))) {
                properties.setProperty(USER_NICKNAME, nickname);
            }
            FileOutputStream fos = new FileOutputStream(CONFIG_PATH.toString());
            properties.store(fos, "update config");
        } catch (IOException ex) {
            throw new LogicException("config.error", "保存配置错误");
        }
    }

    @Override
    public User getUser() {
        loadConfig();
        user = new User();
        user.setUsername(this.blogConfig.getLoginName());
        user.setEmail(this.blogConfig.getEmail());
        user.setAvatar(this.blogConfig.getAvatar());
        return user;
    }

    @Override
    public void updateConfig(BlogConfig blogConfig) {
        loadConfig();
        save(blogConfig);
    }

    @Override
    public void updateUser(User user) {
        loadConfig();
        final String username = user.getUsername();
        final String avatar = user.getAvatar();
        final String email = user.getEmail();
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        blogConfig.setNickname(username);
        blogConfig.setAvatar(avatar);
        blogConfig.setPassword(password);
        blogConfig.setEmail(email);
        save(blogConfig);
    }

    /**
     * 加载本地配置
     */
    private void loadConfig() {
        if(blogConfig == null) {
            synchronized (this) {
                if(blogConfig == null) {
                    load();
                }
            }
        }
    }

    /**
     * 从本地文件系统中加载配置
     */
    private void load() {
        // 登录名称
        String loginName = properties.getProperty(LOGIN_NAME);
        // 登录密码
        String loginPwd = properties.getProperty(LOGIN_PWD);
        // 用户邮箱
        String userEmail = properties.getProperty(USER_EMAIL);
        // 用户昵称
        String nickname = properties.getProperty(USER_NICKNAME);
        // 用户头像
        String avatar = properties.getProperty(USER_AVATAR);
        // 评论策略
        String commentStrategy = properties.getProperty(COMMENT_STRATEGY, CommentStrategy.NEVER.name());

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

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("method<afterPropertiesSet> initial success");
        try {
            if(!Files.exists(CONFIG_PATH)) {
                FileUtil.createFile(CONFIG_PATH);
                initConfig();
            }
            properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(new FileSystemResource(CONFIG_PATH),
                    StandardCharsets.UTF_8));
        } catch (IOException ex) {
            logger.error("读取配置文件失败:[{}]", ex.getMessage(), ex);
            throw new RuntimeException("配置文件初始化失败");
        }
    }

    /**
     * 初始化配置文件
     * @throws IOException IOException
     */
    private void initConfig() throws IOException {
        properties = new Properties();
        properties.setProperty(LOGIN_NAME, Constant.DEFAULT_LOGIN_NAME);
        properties.setProperty(LOGIN_PWD, DigestUtils.md5DigestAsHex(Constant.DEFAULT_LOGIN_PWD.getBytes(StandardCharsets.UTF_8)));
        properties.setProperty(USER_EMAIL, "");
        properties.setProperty(USER_AVATAR, "");
        properties.setProperty(USER_NICKNAME, Constant.DEFAULT_USER_NICKNAME);
        properties.setProperty(COMMENT_STRATEGY, CommentStrategy.NEVER.name());

        FileOutputStream fos = new FileOutputStream(CONFIG_PATH.toString());
        properties.store(fos, "initial properties");
        fos.close();
    }
}
