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
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "用户名只能由数字或者英文字母组成")
    @NotBlank(message = "用户名不能为空")
    @Length(message = "用户名长度必须在 {min} 和 {max} 之间", max = 10, min = 3)
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
