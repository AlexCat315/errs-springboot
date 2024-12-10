package com.x.backend.service;

import com.x.backend.pojo.dto.AccountDTO;
import com.x.backend.pojo.entity.Account;

public interface AccountService {
    Account login(AccountDTO accountDTO);

    void setToken(String token,Account account);

    void findByEmail(String email);

    void register(Account account);
}
