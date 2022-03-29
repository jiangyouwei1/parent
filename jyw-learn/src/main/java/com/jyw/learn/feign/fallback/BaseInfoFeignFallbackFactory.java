package com.jyw.learn.feign.fallback;

import com.jyw.learn.common.bean.AjaxResult;
import com.jyw.learn.feign.BaseInfoFeign;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BaseInfoFeignFallbackFactory implements FallbackFactory<BaseInfoFeign> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public BaseInfoFeign create(Throwable throwable) {
        return new BaseInfoFeign() {
            @Override
            public AjaxResult queryBaseInfoPage() {
                logger.info("====={}",throwable);
                return AjaxResult.failure("10900","未知异常");
            }
        };
    }
}
