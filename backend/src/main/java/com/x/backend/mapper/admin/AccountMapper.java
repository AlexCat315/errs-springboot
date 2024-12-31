package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.dto.FindInviteCodeDTO;
import com.x.backend.pojo.admin.dto.ForgotPasswordDTO;
import com.x.backend.pojo.admin.entity.Account;
import com.x.backend.pojo.admin.entity.Invite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

    Account login(String username);

    @Select("select count(t_id) from token where a_id = #{aId}")
    Integer findTokenByAId(Integer aId);

    @Insert("insert into token (a_id, token) values (#{aId}, #{token})")
    Integer insertToken(Integer aId, String token);

    @Update("update token set token = #{token} where a_id = #{aId}")
    Integer updateToken(String token, Integer aId);

    @Select("select a_id from account where email = #{email}")
    Integer findByEmail(String email);

    Integer insert(Account account);

    @Update("update account set password = #{newPassword} where email = #{email}")
    Integer updatePassword(ForgotPasswordDTO forgotPasswordDTO);

    FindInviteCodeDTO findByInviteCode(String inviteCode);

    Integer insertInvite(Invite invite);

    @Select("select a_id,username,email from account where username = #{username} or email = #{username}")
    Account findAccountByNameOrEmail(String username);
}
