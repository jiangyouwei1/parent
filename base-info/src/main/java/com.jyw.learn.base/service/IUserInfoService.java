package com.jyw.learn.base.service;

import com.jyw.learn.base.vo.UserInfoVo;

import java.util.Map;

public interface IUserInfoService {
    UserInfoVo findUserByNameAndPwd(Map<String, Object> credentials);
}
