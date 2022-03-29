package com.jyw.learn.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyw.learn.base.bean.UserResourceVo;
import com.jyw.learn.base.service.IUserResourceService;
import com.jyw.learn.common.bean.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class UserResourceController {
    @Autowired
    private IUserResourceService service;
    @RequestMapping(value = "/getresource")
    @ResponseBody
    public AjaxResult findAll(){
        return AjaxResult.successWithData(service.findAll());
    }
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        return "调用成功";
    }

    @RequestMapping(value = "/getresourcepage")
    @ResponseBody
    public AjaxResult findAllPage(){
        //测试熔断超时
        try{
            Thread.sleep(9900);
        }catch (Exception e){

        }

        IPage<UserResourceVo> data  = service.findAllPage();
        return AjaxResult.successWithData(data);
    }
}
