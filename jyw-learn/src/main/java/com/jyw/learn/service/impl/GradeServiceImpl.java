package com.jyw.learn.service.impl;

import com.jyw.learn.dao.IRedisDao;
import com.jyw.learn.mapper.GradesMapper;
import com.jyw.learn.model.GradesInfo;
import com.jyw.learn.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GradeServiceImpl implements IGradeService {
    @Resource
    private GradesMapper gradesMapper;

    @Autowired
    private IRedisDao redisDao;

    @Override
    public GradesInfo getGradeById(Integer id) {
        return gradesMapper.getGradesInfo(id);
    }

    @Override
    public void setGradeCache(GradesInfo gradesInfo) {
        redisDao.setKey("grade:id"+gradesInfo.getId(),String.valueOf(gradesInfo.getStudentNum()));
        System.out.println(redisDao.getKey("grade:id"+gradesInfo.getId()));
    }

    @Override
    public void removeGradeCache(Integer id) {
        String key = "grade:id"+id;
        System.out.println("key======="+key);
        System.out.println("删除前：==="+redisDao.getKey(key));

        redisDao.delKey(key);

        System.out.println("删除后：==="+redisDao.getKey(key));
    }

    @Override
    public void updateGradeNum(GradesInfo gradesInfo) {
        gradesMapper.updateStudentNum(gradesInfo);
    }

    /**
     * 从缓存中获取班级信息
     * @param gradId
     * @return
     */
    @Override
    public GradesInfo getGradeByCache(Integer gradId) {
        String key = "grader:id"+gradId;
        String result = redisDao.getKey(key);
        if(result!=null && !"".equals(result)){
            return new GradesInfo(gradId,Integer.parseInt(result) );
        }
        return null;
    }


}
