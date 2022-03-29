package com.jyw.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyw.learn.base.BaseEntity;
import lombok.Data;

@TableName("tm_task")
@Data
public class TmTask extends BaseEntity {
    @TableId(value="ID", type = IdType.AUTO)
    private Long id;
    @TableField("start_date")
    private String startDate;
    @TableField("end_date")
    private String endDate;
    @TableField("task_key")
    private String taskKey;
    @TableField("task_desc")
    private String taskDesc;
}
