package com.x.backend.pojo.admin.vo.request.account;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForgotPasswordVo implements Serializable {
    private String email;
    private String password;
    private String confirmPassword;
    private String code;
}
