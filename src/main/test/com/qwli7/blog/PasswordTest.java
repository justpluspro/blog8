package com.qwli7.blog;

import org.springframework.util.DigestUtils;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

public class PasswordTest {


    @Test
    public void test_get_password() {
        String password = "123456";

        String s = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }
}
