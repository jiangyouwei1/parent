package com.jyw.learn.controller;

import com.jyw.learn.model.UserInfo;
import com.jyw.learn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public UserInfo getUserInfo() {
        UserInfo user = userService.findUserInfo();
        return user;
    }
    @RequestMapping("getCacheUser")
    @ResponseBody
    public UserInfo getCacheUser() {
        return userService.getCachedUserInfo();
    }

}
