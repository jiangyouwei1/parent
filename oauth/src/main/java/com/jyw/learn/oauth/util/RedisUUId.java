package com.jyw.learn.oauth.util;
import cn.hutool.crypto.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUUId {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    // 过期时间
    private final static long expiration = 1800;

    // 过期前1分钟
    private final static long lastTime =  60;

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
        logger.info("=====key有效时间{}",redisTemplate.boundHashOps(key).getExpire());
        return redisTemplate.opsForValue().get(key);
    }
}
