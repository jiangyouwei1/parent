package com.jyw.learn.model;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduledTaskBean {
    /**
     * 任务key值 唯一
     */
    private String taskKey;
    /**
     * 任务描述
     */
    private String taskDesc;

    private Date startDate;

    private String endDate;

    private Integer pageNo;

    private Integer pageSize;

}