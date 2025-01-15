package com.x.backend.pojo.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class Account {
    private Integer aId; // 对应 Kotlin 的 Int?
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