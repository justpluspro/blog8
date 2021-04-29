package com.qwli7.blog.entity.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改密码
 * @author liqiwen
 * @since 1.2
 */
public class UpdatedPassword implements Serializable {
    /**
     * 旧密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Length(max = 16, min = 6, message = "确认密码长度必须在 {min} 和 {max} 之间")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Length(max = 16, min = 6, message = "确认密码长度必须在 {min} 和 {max} 之间")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Length(max = 16, min = 6, message = "确认密码长度必须在 {min} 和 {max} 之间")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
