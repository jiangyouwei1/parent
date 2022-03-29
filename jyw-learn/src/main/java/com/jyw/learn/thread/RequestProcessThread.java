package com.jyw.learn.thread;

import com.jyw.learn.myrequest.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 请求线程
 */
public class RequestProcessThread implements Callable {
    /**
     * 各自线程监控的内存队列
     */
    private ArrayBlockingQueue<Request> queue;
    public RequestProcessThread(ArrayBlockingQueue<Request> queue){
        this.queue = queue;
    }
    @Override
    public Boolean call() throws Exception {
        try{
            while (true){
                /**
                 * 拿到队列中的请求
                 * blockingqueue 当队列满了或者空时，线程被阻塞
                 */
                Request request = queue.take();
                //拿到请求后执行
                request.process();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
}
