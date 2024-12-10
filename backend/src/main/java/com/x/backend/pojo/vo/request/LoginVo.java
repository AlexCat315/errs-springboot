package com.x.backend.pojo.vo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {
    private String username;
    private String password;
    private Boolean rememberMe; //记住我
}
