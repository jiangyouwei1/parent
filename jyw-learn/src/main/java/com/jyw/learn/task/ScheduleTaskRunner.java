package com.jyw.learn.task;

import com.jyw.learn.model.ScheduledTaskBean;
import com.jyw.learn.service.IScheduleTaskService;
import com.jyw.learn.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Order(value = 1)
public class ScheduleTaskRunner implements ApplicationRunner {
    @Autowired
    IScheduleTaskService service;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String nowDateStr = parseDateToStr("yyyy-MM-dd HH:mm:ss",new Date());
        List<ScheduledTaskBean> beanList = service.query(nowDateStr);
        System.out.println(GsonUtil.obj2Json(beanList));
        service.initTask(beanList);
    }

    public static void main(String[] args) {
        String nowDateStr = parseDateToStr("yyyy-MM-dd HH:mm:ss",new Date());
        System.out.println(nowDateStr);
    }

    public static final String parseDateToStr(String format, Date date) {
        return (new SimpleDateFormat(format)).format(date);
    }
}
