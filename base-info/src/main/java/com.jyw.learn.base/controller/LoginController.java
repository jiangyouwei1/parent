package com.jyw.learn.base.controller;

import cn.hutool.core.util.ObjectUtil;
import com.jyw.learn.base.bean.ResponseCode;
import com.jyw.learn.base.feign.OauthFeign;
import com.jyw.learn.base.service.IUserInfoService;
import com.jyw.learn.base.vo.UserInfoVo;
import com.jyw.learn.common.bean.AjaxResult;
import com.jyw.learn.common.util.GsonUtil;
import com.wf.captcha.utils.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/userlogin")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    OauthFeign oauthFeign;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(@RequestBody Map<String,Object> credentials,HttpServletRequest request){
        logger.info("===={}",GsonUtil.obj2Json(credentials));
        if(StringUtils.isEmpty(credentials.get("userName"))){
            return AjaxResult.failure(ResponseCode._11005,"账号不能为空!");
        }
        if(StringUtils.isEmpty(credentials.get("password"))){
            return AjaxResult.failure(ResponseCode._11005,"密码不能为空!");
        }
        //校验用户名称跟密码是否正确
        UserInfoVo infoVo = userInfoService.findUserByNameAndPwd(credentials);
        if(ObjectUtil.isNull(infoVo)){
            return AjaxResult.failure(ResponseCode._11005,"用户名和密码不匹配!");
        }
        AjaxResult tokenResult = oauthFeign.getToken(infoVo.getId());
        logger.info("返回的token==={}",tokenResult.getData());
        return tokenResult;
    }

    /**
     * 获取验证码
     * @return
     */
    @RequestMapping(value = "/getVerfyCode",method = RequestMethod.GET)
    public AjaxResult getImage(HttpServletRequest request,
                         HttpServletResponse response) throws Exception{
        CaptchaUtil.out(request, response);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("sessionId",UUID.randomUUID().toString());
        return AjaxResult.successWithData(resultMap);
    }
}
