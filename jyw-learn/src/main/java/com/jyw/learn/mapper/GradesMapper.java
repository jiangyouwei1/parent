package com.jyw.learn.mapper;

import com.jyw.learn.model.GradesInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GradesMapper {
    public void updateStudentNum(GradesInfo gradesInfo);
    @Select("select id ,grades_name as gradesName,student_num as studentNum from tm_grade_info where id=#{id}")
    public GradesInfo getGradesInfo(@Param("id") Integer id);
}
