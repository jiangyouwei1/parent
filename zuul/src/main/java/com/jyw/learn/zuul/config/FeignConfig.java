package com.jyw.learn.zuul.config;

import com.jyw.learn.zuul.util.RedisUUId;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * feign调用不经zuul，所以需要重新手动给feign请求添加一个请求header。否则被调用的服务会拦截此请求
 * @author jyw
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Resource
    private RedisUUId redisUUId;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("secretKey", redisUUId.create("secretKey"));
    }
}
