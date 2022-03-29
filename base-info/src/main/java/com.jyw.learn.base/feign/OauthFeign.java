package com.jyw.learn.base.feign;

import com.jyw.learn.base.feign.fallback.OauthFeignFallbackFactory;
import com.jyw.learn.common.bean.AjaxResult;
import com.jyw.learn.common.conf.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oauth",configuration = FeignConfig.class,fallbackFactory = OauthFeignFallbackFactory.class)
public interface OauthFeign {
    @RequestMapping(value = "/oauth/getToken",method = RequestMethod.POST)
    AjaxResult getToken(@RequestParam("userId") Integer userId);
}
