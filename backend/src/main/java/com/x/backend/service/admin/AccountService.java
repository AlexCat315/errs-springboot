package com.x.backend.service.admin;

import com.x.backend.pojo.dto.AccountDTO;
import com.x.backend.pojo.entity.Account;

public interface AccountService {
    Account login(AccountDTO accountDTO);

    void setToken(String token,Account account);

    void findByEmail(String email);

    void register(Account account);

    void isEmailExists(String email);

    void updatePassword(Account account);
}
