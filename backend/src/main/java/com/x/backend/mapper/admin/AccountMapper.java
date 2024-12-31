package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.dto.FindInviteCodeDTO;
import com.x.backend.pojo.admin.dto.ForgotPasswordDTO;
import com.x.backend.pojo.admin.entity.Account;
import com.x.backend.pojo.admin.entity.Invite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

    Account login(String username);

    @Select("select a_id from account where email = #{email}")
    Integer findByEmail(String email);

    Integer insert(Account account);

    @Update("update account set password = #{newPassword} where email = #{email}")
    Integer updatePassword(ForgotPasswordDTO forgotPasswordDTO);

    FindInviteCodeDTO findByInviteCode(String inviteCode);

    Integer insertInvite(Invite invite);

    @Select("select a_id,username,email from account where username = #{username} or email = #{username}")
    Account findAccountByNameOrEmail(String username);

    @Select("select a_id from account where username = #{username}")
    Account findAccountByName(String username);

    Account findById(Integer id);
}
