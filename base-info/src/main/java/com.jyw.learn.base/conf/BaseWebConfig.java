package com.jyw.learn.base.conf;

import com.jyw.learn.common.interceptor.MyTheadInterceptor;
import com.jyw.learn.common.util.RedisUUId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.jyw.learn"})
public class BaseWebConfig implements WebMvcConfigurer {
    @Autowired
    MyTheadInterceptor myTheadInterceptor;
    @Autowired
    private RedisUUId redisUUID;

    public void addInterceptors(InterceptorRegistry registry){
        myTheadInterceptor.setRedisUUID(redisUUID);
        registry.addInterceptor(myTheadInterceptor);
    }
}
