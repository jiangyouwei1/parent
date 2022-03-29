package com.jyw.learn.listener;

import com.jyw.learn.config.PropertiesValue;
import com.jyw.learn.thread.RequestThreadPool;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 服务启动监听器
 */
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获取配置类的bean  因为监听器的启动优先于spring注册的bean 所以在此手动获取bean
        PropertiesValue propertiesValue= WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(PropertiesValue.class);
        System.out.println("初始化开始++++++++++++++++++++++++++++"+propertiesValue.getThreadPool());
        //初始化线程池+内存队列
        RequestThreadPool.init(propertiesValue.getThreadPool(),propertiesValue.getQueueNum());
        //启动kafka消费者线程




    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
