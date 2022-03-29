package com.jyw.learn.dao.impl;

import com.jyw.learn.dao.IRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisDaoImpl implements IRedisDao {

    @Autowired
    private JedisCluster jedisCluster;
    @Override
    public void setKey(String key, String value) {
        jedisCluster.set(key,value);
    }

    @Override
    public String getKey(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void delKey(String key) {
        jedisCluster.del(key);
    }

    @Override
    public boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public Long incr(String uuidKey, long l) {
        return jedisCluster.incrBy(uuidKey,l);
    }

    @Override
    public void setExpires(String uuidKey, int i) {
        jedisCluster.pexpire(uuidKey,i);
    }
}
