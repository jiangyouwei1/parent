package com.jyw.learn.base.mapper;

import com.jyw.learn.base.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserInfoMapper {
    UserInfoVo findUserByNameAndPwd(@Param("params") Map<String, Object> credentials);
}
