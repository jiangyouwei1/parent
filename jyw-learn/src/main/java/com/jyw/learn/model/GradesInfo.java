package com.jyw.learn.model;

import lombok.Data;

/**
 * 班级信息
 */
@Data
public class GradesInfo {
    private Integer id;
    private String gradesName;
    private Integer studentNum;//学生数量
    public GradesInfo(Integer id,Integer studentNum){
        this.id = id;
        this.studentNum = studentNum;
    }
    public GradesInfo(){

    }
}
