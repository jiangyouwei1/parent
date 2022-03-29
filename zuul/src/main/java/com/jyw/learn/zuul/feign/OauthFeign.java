package com.jyw.learn.zuul.feign;

import com.jyw.learn.zuul.config.FeignConfig;
import com.jyw.learn.zuul.bean.AjaxResult;
import com.jyw.learn.zuul.feign.fallback.OauthFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oauth",configuration = FeignConfig.class,fallbackFactory = OauthFeignFallbackFactory.class)
public interface OauthFeign {
    @RequestMapping(value = "/oauth/checkToken",method = RequestMethod.POST)
    AjaxResult checkToken(@RequestParam("accessToken") String accessToken);
}
