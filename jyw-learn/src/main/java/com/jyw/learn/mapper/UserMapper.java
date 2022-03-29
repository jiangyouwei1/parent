package com.jyw.learn.mapper;

import com.jyw.learn.model.UserInfo;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    /**
     * 查询用户信息
     * @return 用户信息
     */
    @Select("select name,age from user;")
    public UserInfo findUserInfo();

}
