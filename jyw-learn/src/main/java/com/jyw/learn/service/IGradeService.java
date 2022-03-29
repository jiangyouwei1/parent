package com.jyw.learn.service;

import com.jyw.learn.model.GradesInfo;

public interface IGradeService {

    public GradesInfo getGradeById(Integer id);

    public void setGradeCache(GradesInfo gradesInfo);

    public void removeGradeCache(Integer id);

    public void updateGradeNum(GradesInfo gradesInfo);

    public GradesInfo getGradeByCache(Integer gradId);
}
