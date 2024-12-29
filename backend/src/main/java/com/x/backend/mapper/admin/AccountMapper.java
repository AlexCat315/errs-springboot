package com.x.backend.mapper.admin;

import com.x.backend.pojo.dto.AccountDTO;
import com.x.backend.pojo.dto.ForgotPasswordDTO;
import com.x.backend.pojo.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

    Account login(AccountDTO accountDTO);

    @Select("select count(t_id) from token where a_id = #{aId}")
    Integer findTokenByAId(Integer aId);

    @Insert("insert into token (a_id, token) values (#{aId}, #{token})")
    Integer insertToken(Integer aId, String token);

    @Update("update token set token = #{token} where a_id = #{aId}")
    Integer updateToken(String token, Integer aId);

    @Select("select a_id from account where email = #{email}")
    Integer findByEmail(String email);

    @Insert("insert into account (email, password, username, role) values (#{email}, #{password}, #{username}, #{role})")
    Integer insert(Account account);

    @Update("update account set password = #{newPassword} where email = #{email}")
    Integer updatePassword(ForgotPasswordDTO forgotPasswordDTO);
}
