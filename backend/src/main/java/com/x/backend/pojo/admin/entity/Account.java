package com.x.backend.pojo.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Account implements Serializable {
    private Integer aId;
    private String username;
    private String password;
    private String email;
    private String role;
    private String vip;
    private Date createdAt; // 创建时间
    private String inviteCode; // 邀请码
    // 是否封禁
    private Boolean isBanned; // true:封禁 false:未封禁
}
