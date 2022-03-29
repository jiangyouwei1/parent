package com.jyw.learn.feign;

import com.jyw.learn.common.bean.AjaxResult;
import com.jyw.learn.common.conf.FeignConfig;
import com.jyw.learn.feign.fallback.BaseInfoFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "base-info",configuration = FeignConfig.class,fallbackFactory = BaseInfoFeignFallbackFactory.class)
public interface BaseInfoFeign {
    @ResponseBody
    @RequestMapping(value = "/user/getresourcepage",method = RequestMethod.GET)
    AjaxResult queryBaseInfoPage();
}
