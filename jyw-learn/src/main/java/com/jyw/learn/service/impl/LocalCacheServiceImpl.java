package com.jyw.learn.service.impl;

import com.jyw.learn.model.GradesInfo;
import com.jyw.learn.service.ILocalCacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LocalCacheServiceImpl implements ILocalCacheService {
    public static final String CACHE_NAME = "local";
    @Override
    @CachePut(value=CACHE_NAME , key = "'key_' + #gradesInfo.getId()")
    public GradesInfo saveGragdeLocalCache(GradesInfo gradesInfo) {
        return gradesInfo;
    }

    @Override
    @Cacheable(value=CACHE_NAME ,key="'key_' + #gradeId")
    public GradesInfo getGradeLocalCache(Integer gradeId) {
        return null;
    }

    @Override
    @CachePut(value = CACHE_NAME,key = "'mykey_'+#map.keySet()")
    public List<Map<String,Object>> saveDataLocalCache(Map<String, Object> map) {
        return (List<Map<String, Object>>) map.get("datainit");
    }

    @Override
    @Cacheable(value = CACHE_NAME,key="'mykey_'+#key")
    public List<Map<String, Object>> getInitDataInfo(String key) {
        return null;
    }
}
