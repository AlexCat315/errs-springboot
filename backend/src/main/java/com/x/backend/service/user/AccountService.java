package com.x.backend.service.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.user.vo.request.account.LoginVo;

public interface AccountService {
        
    ResultEntity<String> login(LoginVo loginVo);
} 
