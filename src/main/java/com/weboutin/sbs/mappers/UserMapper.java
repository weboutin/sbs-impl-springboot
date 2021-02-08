package com.weboutin.sbs.mappers;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import com.weboutin.sbs.entity.User;

@Mapper
public interface UserMapper {
    @Select("select id userId,account,password from `sbs-users` where account = #{account}")
    User selectUserByAccount(String account);

    @Insert("insert into `sbs-users` (account,password,created_at,modified_at) values (#{user.account},#{user.password},#{user.createdAt},#{user.modifiedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "user.userId", keyColumn = "id")
    int insertUser(@Param("user") User user);
}