package com.qwli7.blog.entity.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 登录实体类
 * @author liqiwen
 * @since 1.0
 */
public class LoginModel implements Serializable {

    /**
     * 只能由 26 个字母组成的用户名
     */
    @Pattern(regexp = "^[A-Za-z]+$")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(message = "密码长度必须在 {min} ~ {max} 之间", min = 6, max = 18)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
