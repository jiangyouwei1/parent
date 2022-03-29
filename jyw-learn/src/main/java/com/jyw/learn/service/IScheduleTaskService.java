package com.jyw.learn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyw.learn.base.IBaseService;
import com.jyw.learn.entity.TmTask;
import com.jyw.learn.model.ScheduledTaskBean;

import java.util.List;

public interface IScheduleTaskService extends IBaseService<TmTask> {
    List<ScheduledTaskBean> query(String nowDateStr);

    void initTask(List<ScheduledTaskBean> beanList);

    public Boolean restart(String taskKey);

    public Boolean stop(String taskKey);

    public Boolean start(String taskKey);

    ScheduledTaskBean queryById(Integer id);

    IPage<TmTask> queryByNamePage();
}
