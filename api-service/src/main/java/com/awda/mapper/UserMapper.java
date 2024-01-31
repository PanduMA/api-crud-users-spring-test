package com.awda.mapper;

import com.awda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("select id,user_name as userName,account_number as accountNumber,email_address as emailAddress,identity_number as identityNumber,password from user")
    List<User> findAll();

    @Select("select id,user_name as userName,account_number as accountNumber,email_address as emailAddress,identity_number as identityNumber,password from user where id=#{id}")
    Optional<User> findById(Long id);

    @Select("select id,user_name as userName,account_number as accountNumber,email_address as emailAddress,identity_number as identityNumber,password from user where email_address=#{emailAddress}")
    Optional<User> findByEmail(String emailAddress);

    @Insert("insert into user(user_name,account_number,email_address,identity_number,password) values (#{userName},#{accountNumber},#{emailAddress},#{identityNumber},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void store(User user);

    @Update("update user set user_name=#{user.userName}, account_number=#{user.accountNumber}, email_address=#{user.emailAddress}, identity_number=#{user.identityNumber}, password=#{user.password} where id=#{id}")
    void update(User user, Long id);

    @Delete("delete from user where id=#{id}")
    Boolean deleteById(Long id);

    @Select("select last_insert_id() from user")
    Long getLastId();
}
