package com.jyw.learn.task;

import com.jyw.learn.service.ScheduledTaskJob;
import org.springframework.stereotype.Controller;

@Controller
public class Task01 implements ScheduledTaskJob {

//    @Scheduled(cron = "0 */1 * * * ?")
//    public void test(){
//        System.out.println("测试测试111111");
//    }

    @Override
    public void run() {
        System.out.println("开始执行任务");
    }
}
