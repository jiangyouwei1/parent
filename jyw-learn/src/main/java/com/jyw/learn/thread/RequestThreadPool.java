package com.jyw.learn.thread;
import com.jyw.learn.myrequest.Request;
import com.jyw.learn.myrequest.RequestQueue;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求线程池
 */
@Configuration
public class RequestThreadPool {
    private static int THREAD_POOL_NUM;
    private static int QUEUE_NUM;
    //    @Value("${jyw.queue.num}")
//    private int QUEUE_NUM;//内存队列个数
    /**
     * 线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_NUM);


    public RequestThreadPool(){
        RequestQueue queues = RequestQueue.getInstance();
        for (int i = 0; i < THREAD_POOL_NUM; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue(QUEUE_NUM);
            queues.addQueue(queue);
            executorService.submit(new RequestProcessThread(queue));
        }
    }

    /**
     * 内部类绝对线程安全
     */
    public static class Singleton{
        public static RequestThreadPool instanse;
        static {
            instanse = new RequestThreadPool();
        }
        public static RequestThreadPool getInstanse(){
            return instanse;
        }
    }
    public static RequestThreadPool getInstance(){
        return Singleton.getInstanse();
    }
    public static void init(int threadPoolNum,int queueNum){
        THREAD_POOL_NUM = threadPoolNum;
        QUEUE_NUM = queueNum;
        getInstance();
    }
}
