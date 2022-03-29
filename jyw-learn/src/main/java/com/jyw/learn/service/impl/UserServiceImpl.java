package com.jyw.learn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jyw.learn.dao.IRedisDao;
import com.jyw.learn.mapper.UserMapper;
import com.jyw.learn.model.UserInfo;
import com.jyw.learn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private IRedisDao redisDao;

    @Override
    public UserInfo findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public UserInfo getCachedUserInfo() {
        redisDao.setKey("cache_user","{\"name\":\"jyw\",\"age\":18}");
        String userStr = redisDao.getKey("cache_user");
        JSONObject userJsonObject = JSONObject.parseObject(userStr);
        UserInfo userInfo = new UserInfo();
        userInfo.setName(String.valueOf(userJsonObject.get("name")));
        userInfo.setAge(String.valueOf(userJsonObject.get("age")));
        return userInfo;
    }
}
