package com.x.backend.pojo.common;

import lombok.Data;

import java.util.Date;

@Data
public class Account {
    protected Integer aId;
    protected String username;
    protected String password;
    protected String email;
    protected String role;
    protected String vip;
    protected Date createdAt; // 创建时间
    protected String inviteCode; // 邀请码
    // 是否封禁
    protected Boolean isBanned; // true:封禁 false:未封禁
}
