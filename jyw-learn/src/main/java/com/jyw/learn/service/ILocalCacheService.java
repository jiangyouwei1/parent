package com.jyw.learn.service;

import com.jyw.learn.model.GradesInfo;

import java.util.List;
import java.util.Map;

/**
 * 本地缓存接口类
 */
public interface ILocalCacheService {
    /**
     * 将缓存信息保存在本地缓存中
     * @param gradesInfo
     * @return
     */
    public GradesInfo saveGragdeLocalCache(GradesInfo gradesInfo);

    /**
     * 在本地缓存中获取班级信息
     * @param gradeId
     * @return
     */
    public GradesInfo getGradeLocalCache(Integer gradeId);

    /**
     * 数据初始化
     */
    public List<Map<String,Object>> saveDataLocalCache(Map<String,Object> map);

    public List<Map<String,Object>> getInitDataInfo(String key);
}
