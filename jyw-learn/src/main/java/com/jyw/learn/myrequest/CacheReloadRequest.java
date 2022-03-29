package com.jyw.learn.myrequest;

import com.jyw.learn.model.GradesInfo;
import com.jyw.learn.service.IGradeService;

/**
 * 重新加载缓存
 */
public class CacheReloadRequest implements Request {
    private GradesInfo gradesInfo;
    private IGradeService gradeService;
    public CacheReloadRequest(GradesInfo gradesInfo,IGradeService gradeService){
        this.gradesInfo = gradesInfo;
        this.gradeService = gradeService;
    }
    @Override
    public void process() {
        //读取数据
        gradesInfo =  gradeService.getGradeById(gradesInfo.getId());
        //重载缓存
        gradeService.setGradeCache(gradesInfo);
    }

    @Override
    public Integer getId() {
        return gradesInfo.getId();
    }

    @Override
    public boolean idForceRefresh() {
        return false;
    }
}
