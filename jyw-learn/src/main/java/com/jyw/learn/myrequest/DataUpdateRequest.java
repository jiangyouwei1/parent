package com.jyw.learn.myrequest;

import com.jyw.learn.model.GradesInfo;
import com.jyw.learn.service.IGradeService;

/**
 * 数据个更新请求
 * 1.删除缓存
 * 2.更新数据库
 */
public class DataUpdateRequest implements Request {

    private GradesInfo gradesInfo;

    private IGradeService gradeService;

    public DataUpdateRequest(GradesInfo gradesInfo,IGradeService gradeService){
        this.gradesInfo = gradesInfo;
        this.gradeService = gradeService;
    }

    @Override
    public void process() {
        //删除redis缓存
        gradeService.removeGradeCache(gradesInfo.getId());
        //修改数据库中的数据
        gradeService.updateGradeNum(gradesInfo);

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
