package com.x.backend.pojo.user.vo.request.account;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPassowrdVo implements Serializable {
    
    @NotBlank(message = "邮箱不能为空") // 非空校验
    @Email(message = "邮箱格式错误") // 格式校验
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码必须为6位")
    private String code;

    @NotBlank(message = "密码不能为空")
    private String password;
    
    @NotBlank(message = "密码不能为空")
    private String repeatPassword;

    
}
