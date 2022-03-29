package com.jyw.learn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyw.learn.common.bean.AjaxResult;
import com.jyw.learn.entity.TmTask;
import com.jyw.learn.feign.BaseInfoFeign;
import com.jyw.learn.mapper.ScheduleTaskMapper;
import com.jyw.learn.model.ScheduledTaskBean;
import com.jyw.learn.service.impl.ScheduleTaskServiceImpl;
import com.jyw.learn.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ScheduleTaskServiceImpl service;
    @Autowired
    private ScheduleTaskMapper taskMapper;
    @Autowired
    BaseInfoFeign baseInfoFeign;
    @RequestMapping(value = "/refrash/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String refrashSchedule(@PathVariable("id") Integer id){
        ScheduledTaskBean bean = service.queryById(id);
        logger.info("刷新的对象==={}", GsonUtil.obj2Json(bean));
        service.restart(bean.getTaskKey());
        return "刷新成功！";
    }
    @RequestMapping(value = "/getPage",method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getPage(){

        IPage<TmTask> data =  service.queryByNamePage();
        logger.info("===={}",GsonUtil.obj2Json(data));
        return AjaxResult.successWithData("10000",data);
    }

    @RequestMapping(value = "/getBaseinfoPage",method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getBaseinfoPage(){


        return AjaxResult.successWithData(baseInfoFeign.queryBaseInfoPage());
    }
}
