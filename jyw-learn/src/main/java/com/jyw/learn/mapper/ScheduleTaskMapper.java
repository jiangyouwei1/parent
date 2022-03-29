package com.jyw.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyw.learn.entity.TmTask;
import com.jyw.learn.model.ScheduledTaskBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ScheduleTaskMapper extends BaseMapper<TmTask> {

    List<ScheduledTaskBean> query(String nowDateStr);

    ScheduledTaskBean getByKey(String taskKey);

    ScheduledTaskBean queryById(Integer id);

    IPage<TmTask> taskPage(IPage<ScheduledTaskBean> page, ScheduledTaskBean taskBean);
}
