package com.x.backend.pojo.user.vo.request.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginVo {
     private String username;
     private String password;
}
