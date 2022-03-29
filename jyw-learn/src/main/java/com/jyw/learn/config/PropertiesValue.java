package com.jyw.learn.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量配置类
 */
@Component
@Data
public class PropertiesValue {
    @Value("${threadpoolNum}")
    public int threadPool;
    @Value("${queueNum}")
    public int queueNum;
    public PropertiesValue(){

    }
}
