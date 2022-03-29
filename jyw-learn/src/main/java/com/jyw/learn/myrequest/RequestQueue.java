package com.jyw.learn.myrequest;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求内存队列
 */
@Data
public class RequestQueue {

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    /**
     * 标识位，对相同请求去重
     */
    Map<Integer,Boolean> flagMap = new ConcurrentHashMap<>();

    private static class Sington{
        public static RequestQueue instanse;
        static {
            instanse = new RequestQueue();
        }
        public static RequestQueue getInstanse(){
            return instanse;
        }
    }

    public static RequestQueue getInstance(){
        return Sington.getInstanse();
    }
    //添加一个内存队列
    public void addQueue(ArrayBlockingQueue<Request> queue){
        queues.add(queue);
    }

    public ArrayBlockingQueue<Request> getQueueByIndex(int index){
        return queues.get(index);
    }
    public Integer getQueuueSize(){
        return queues.size();
    }
}
