package com.jyw.learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyw.learn.base.BaseServiceImpl;
import com.jyw.learn.entity.TmTask;
import com.jyw.learn.mapper.ScheduleTaskMapper;
import com.jyw.learn.model.ScheduledTaskBean;
import com.jyw.learn.service.IScheduleTaskService;
import com.jyw.learn.service.ScheduledTaskJob;
import com.jyw.learn.util.CronUtil;
import com.jyw.learn.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ScheduleTaskServiceImpl extends BaseServiceImpl<ScheduleTaskMapper, TmTask> implements IScheduleTaskService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ScheduleTaskMapper taskMapper;

    @Autowired
    @Qualifier(value = "scheduledTaskJobMap")
    private Map<String, ScheduledTaskJob> scheduledTaskJobMap;

    /**
     * 可重入锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 定时任务线程池
     */
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 存放已经启动的任务map
     */
    private Map<String, ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<>();

    @Override
    public List<ScheduledTaskBean> query(String nowDateStr) {
        List<ScheduledTaskBean> resultList = taskMapper.query(nowDateStr);
        logger.info("查询结果==={}", GsonUtil.obj2Json(resultList));
        return resultList;
    }

    @Override
    public void initTask(List<ScheduledTaskBean> scheduledTaskBeanList) {
        if (CollectionUtils.isEmpty(scheduledTaskBeanList)) {
            return;
        }
        for (ScheduledTaskBean scheduledTask : scheduledTaskBeanList) {
            //任务 key
            String taskKey = scheduledTask.getTaskKey();
            //校验是否已经启动
            if (this.isStart(taskKey)) {
                continue;
            }
            //启动任务
            this.doStartTask(scheduledTask);
        }
    }
    /**
     * 根据任务key 启动任务
     */
    @Override
    public Boolean start(String taskKey) {
        logger.info(">>>>>> 启动任务 {} 开始 >>>>>>", taskKey);
        //添加锁放一个线程启动，防止多人启动多次
        lock.lock();
        logger.info(">>>>>> 添加任务启动锁完毕");
        try {
            //校验是否已经启动
            if (this.isStart(taskKey)) {
                logger.info(">>>>>> 当前任务已经启动，无需重复启动！");
                return false;
            }
            //校验任务是否存在
            if (!scheduledTaskJobMap.containsKey(taskKey)) {
                return false;
            }
            //根据key数据库获取任务配置信息
            ScheduledTaskBean scheduledTask = taskMapper.getByKey(taskKey);
            //启动任务
            this.doStartTask(scheduledTask);
        } finally {
            // 释放锁
            lock.unlock();
            logger.info(">>>>>> 释放任务启动锁完毕");
        }
        logger.info(">>>>>> 启动任务 {} 结束 >>>>>>", taskKey);
        return true;
    }

    @Override
    public ScheduledTaskBean queryById(Integer id) {
        return taskMapper.queryById(id);
    }

    @Override
    public IPage<TmTask> queryByNamePage() {
        IPage<ScheduledTaskBean> page = new Page<ScheduledTaskBean>(1,2);
        QueryWrapper<TmTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("task_key","task");
        ScheduledTaskBean task = new ScheduledTaskBean();
        task.setTaskKey("task");
       IPage<TmTask> pageTask = taskMapper.taskPage(page,task);
       logger.info("===={}",pageTask.getTotal());
        return  taskMapper.taskPage(page,task);
    }

    /**
     * 根据 key 停止任务
     */
    @Override
    public Boolean stop(String taskKey) {
        logger.info(">>>>>> 进入停止任务 {}  >>>>>>", taskKey);
        //当前任务实例是否存在
        boolean taskStartFlag = scheduledFutureMap.containsKey(taskKey);
        logger.info(">>>>>> 当前任务实例是否存在 {}", taskStartFlag);
        if (taskStartFlag) {
            //获取任务实例
            ScheduledFuture scheduledFuture = scheduledFutureMap.get(taskKey);
            //关闭实例
            scheduledFuture.cancel(true);
        }
        logger.info(">>>>>> 结束停止任务 {}  >>>>>>", taskKey);
        return taskStartFlag;
    }
    @Override
    public Boolean restart(String taskKey) {
        logger.info(">>>>>> 进入重启任务 {}  >>>>>>", taskKey);
        //先停止
        this.stop(taskKey);
        //再启动
        return this.start(taskKey);
    }
    /**
     * 执行启动任务
     */
    private void doStartTask(ScheduledTaskBean scheduledTask) {
        //任务key
        String taskKey = scheduledTask.getTaskKey();
        //定时表达式
        String taskCron = CronUtil.getCron(scheduledTask.getStartDate());
        //获取需要定时调度的接口
        ScheduledTaskJob scheduledTaskJob = scheduledTaskJobMap.get(taskKey);
        ScheduledFuture scheduledFuture = threadPoolTaskScheduler.schedule(scheduledTaskJob,
                new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext triggerContext) {
                        CronTrigger cronTrigger = new CronTrigger(taskCron);
                        return cronTrigger.nextExecutionTime(triggerContext);
                    }
                });
        //将启动的任务放入 map
        scheduledFutureMap.put(taskKey, scheduledFuture);
    }
    private Boolean isStart(String taskKey) {
        //校验是否已经启动
        if (scheduledFutureMap.containsKey(taskKey)) {
            if (!scheduledFutureMap.get(taskKey).isCancelled()) {
                return true;
            }
        }
        return false;
    }
}
