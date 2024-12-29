package com.x.backend.pojo.admin.vo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterVo implements Serializable {
    private String username;
    private String password;
    private String repeatPassword;
    private String email; // 邮箱
    private String code; // 验证码
    private String inviteCode; // 邀请码
}
