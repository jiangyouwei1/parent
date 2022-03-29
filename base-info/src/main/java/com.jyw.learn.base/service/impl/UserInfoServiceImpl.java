package com.jyw.learn.base.service.impl;

import com.jyw.learn.base.mapper.UserInfoMapper;
import com.jyw.learn.base.service.IUserInfoService;
import com.jyw.learn.base.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public UserInfoVo findUserByNameAndPwd(Map<String, Object> credentials) {

        return userInfoMapper.findUserByNameAndPwd(credentials);
    }
}
