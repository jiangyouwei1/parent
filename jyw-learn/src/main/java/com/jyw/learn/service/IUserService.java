package com.jyw.learn.service;

import com.jyw.learn.model.UserInfo;

public interface IUserService {
    /**
     * 查询用户信息
     * @return 用户信息
     */
    public UserInfo findUserInfo();

    /**
     * 查询redis中缓存的用户信息
     * @return
     */
    public UserInfo getCachedUserInfo();
}
