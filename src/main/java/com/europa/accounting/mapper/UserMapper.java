package com.europa.accounting.mapper;

import com.europa.accounting.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username") String username);

    @Insert("insert into user(username, password) value(#{user.username}, #{user.password})")
    int insert(@Param("user") User user);
}
