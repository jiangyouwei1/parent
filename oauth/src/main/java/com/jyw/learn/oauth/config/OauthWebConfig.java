package com.jyw.learn.oauth.config;

import com.jyw.learn.oauth.interceptor.MyTheadInterceptor;
import com.jyw.learn.oauth.util.RedisUUId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.jyw.learn"})
public class OauthWebConfig implements WebMvcConfigurer {
    @Autowired
    MyTheadInterceptor myTheadInterceptor;
    @Autowired
    private RedisUUId redisUUID;

    public void addInterceptors(InterceptorRegistry registry){
        myTheadInterceptor.setRedisUUID(redisUUID);
        registry.addInterceptor(myTheadInterceptor);
    }
}
