package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.User;
import com.qwli7.blog.entity.vo.LoginBean;
import com.qwli7.blog.exception.LoginFailedException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

/**
 * @author qwli7
 * @date 2023/2/16 14:52
 * 功能：blog8
 **/
@Service
public class UserServiceImpl implements UserService {


    private final Path homePath = Paths.get(System.getProperty("user.home"));


    @Override
    public void getUser() {

    }

    @Override
    public User login(LoginBean loginBean) {
        Optional<User> userOptional = readUser();
        if (!userOptional.isPresent()) {
            throw new LoginFailedException(Message.AUTH_FAILED);
        }

        User user = userOptional.get();
        String email = user.getEmail();
        if (!loginBean.getEmail().equals(email)) {
            throw new LoginFailedException(Message.AUTH_FAILED);
        }
        String password = loginBean.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(md5Password)) {
            throw new LoginFailedException(Message.AUTH_FAILED);
        }
        return user;
    }


    private synchronized Optional<User> readUser() {
        Path configPath = Paths.get(homePath.toString(), ".blog_config");
        if (!configPath.toFile().exists()) {
            return Optional.empty();
        }
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(configPath.toFile().toPath()));

            String email = properties.getProperty("blog.email");
            String password = properties.getProperty("blog.password");
            User user = new User();
            user.setPassword(password);
            user.setEmail(email);
            return Optional.of(user);
        } catch (IOException e) {

            return Optional.empty();
        }
    }
}
