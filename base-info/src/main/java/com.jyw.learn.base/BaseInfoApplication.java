package com.jyw.learn.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@MapperScan("com.jyw.learn.base.mapper")
@EnableFeignClients
public class BaseInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseInfoApplication.class,args);
    }
}
