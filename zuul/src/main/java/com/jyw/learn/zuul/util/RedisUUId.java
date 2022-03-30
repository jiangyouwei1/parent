package com.jyw.learn.zuul.util;
import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUUId {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    // 过期时间
    private final static long expiration = 1000 * 60 * 5;

    // 过期前1分钟
    private final static long lastTime = 1000 * 60;

    public String create(String key){
        if(StringUtils.isEmpty(key)){
            return null;
        }
        String secretKey;
        if(redisTemplate.hasKey(key)){
            if(redisTemplate.boundHashOps(key).getExpire() < lastTime){
                redisTemplate.opsForValue().set(key,SecureUtil.md5(UUID.randomUUID().toString()),expiration, TimeUnit.SECONDS);
            }
            secretKey = (String) redisTemplate.opsForValue().get(key);
        }else{
            secretKey = SecureUtil.md5(UUID.randomUUID().toString());
            redisTemplate.opsForValue().set(key,secretKey,expiration,TimeUnit.SECONDS);
        }
        return secretKey;
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
