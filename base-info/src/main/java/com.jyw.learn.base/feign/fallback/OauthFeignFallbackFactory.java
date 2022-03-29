package com.jyw.learn.base.feign.fallback;

import com.jyw.learn.base.feign.OauthFeign;
import com.jyw.learn.common.bean.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OauthFeignFallbackFactory implements FallbackFactory<OauthFeign> {
    @Override
    public OauthFeign create(Throwable throwable) {
        return new OauthFeign() {

            @Override
            public AjaxResult getToken(Integer id) {
                return AjaxResult.failure("10900","未知异常");
            }
        };
    }
}
