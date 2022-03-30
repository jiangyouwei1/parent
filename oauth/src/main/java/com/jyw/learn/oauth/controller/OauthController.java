package com.jyw.learn.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyw.learn.oauth.bean.AjaxResult;
import com.jyw.learn.oauth.util.DefaultAuthenticationKeyGenerator;
import com.jyw.learn.oauth.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/oauth")
public class OauthController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DefaultAuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private final String KEY = "token";
    @Autowired
    RedisUtil redisUtil;

    private final static long lastTime = 1000 * 60;//过期前一分钟

    @Value("${oauth.token.keepTime:1800}")
    private Long keepTime;

    @RequestMapping(value = "/getToken",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getToken(@RequestParam @NotBlank Integer userId){
        Map map = new HashMap();
        map.put("userId",userId);
        JSONObject obj = (JSONObject) JSON.toJSON(map);
        obj.put("time", System.currentTimeMillis());
        String accessToken = authenticationKeyGenerator.generateKey(obj);
        logger.info("-====生成的token:{}",accessToken);
        if(!redisUtil.setWithReturn(KEY+accessToken,accessToken,keepTime)){
            AjaxResult rst = AjaxResult.failure(AjaxResult.ERROR_CODE);
            rst.setMsg("accessToken saving is failed!");
            logger.error("保存到redis失败 :{}", rst);
            return rst;
        }
        obj.clear();
        obj.put("token", accessToken);
        obj.put("expiredIn", keepTime);
        return AjaxResult.successWithData(obj);
    }
    @RequestMapping(value = "/redistest",method = RequestMethod.POST)
    @ResponseBody
    public String test(){
        redisUtil.set("test","11111");
        logger.info("value==={}",redisUtil.get("test"));
        return "success";
    }
    @RequestMapping(value = "/checkToken",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult checkToken(@RequestParam @NotBlank String accessToken){
        if(!StringUtils.isEmpty(redisUtil.get(KEY+accessToken))){
            if(redisUtil.getExpire(KEY+accessToken)<lastTime){
                redisUtil.expire(KEY+accessToken,keepTime);//如果在失效前一分钟内再次调用，重新设置失效时间
            }
            AjaxResult<JSONObject> rst = AjaxResult.success();
            rst.setMsg("access_token is valid!");
            return rst;
        }
        AjaxResult rst = AjaxResult.failure("10992");
        rst.setMsg("access_token is invalid");
        return rst;
    }

}
