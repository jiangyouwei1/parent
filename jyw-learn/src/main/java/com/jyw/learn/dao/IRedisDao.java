package com.jyw.learn.dao;

import java.util.concurrent.TimeUnit;

public interface IRedisDao {
    public void setKey(String key,String value);
    public String getKey(String key);
    public void delKey(String key);
    public boolean exists(String key);

    Long incr(String uuidKey, long l);

    void setExpires(String uuidKey, int i);
}
