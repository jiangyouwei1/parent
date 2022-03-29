package com.jyw.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.jyw.learn.mapper")
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }

}
