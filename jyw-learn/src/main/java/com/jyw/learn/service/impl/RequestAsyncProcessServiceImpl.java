package com.jyw.learn.service.impl;


import com.jyw.learn.myrequest.CacheReloadRequest;
import com.jyw.learn.myrequest.DataUpdateRequest;
import com.jyw.learn.myrequest.Request;
import com.jyw.learn.myrequest.RequestQueue;
import com.jyw.learn.service.IRequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
@Service
public class RequestAsyncProcessServiceImpl implements IRequestAsyncProcessService {
    @Override
    public void process(Request request) {
        RequestQueue queue = RequestQueue.getInstance();
        Map<Integer,Boolean> flagMap = queue.getFlagMap();
        if(request instanceof DataUpdateRequest){
            flagMap.put(request.getId(),true);//将唯一标识设置未true
        }else if(request instanceof CacheReloadRequest){//读请求

            if(flagMap.get(request.getId()) == null){//如果此数据从未被读取，只有当request.getId()值被执行写请求后才可以读取
                flagMap.put(request.getId(),false);
            }


            if(flagMap.get(request.getId()) != null  && flagMap.get(request.getId())){//不为空且 为true 说明有读请求执行
                flagMap.put(request.getId(),false);//将此标识为置为false
            }
            //对读请求去重
            if(flagMap.get(request.getId()) != null && !flagMap.get(request.getId())){
                //此时最新的数据已被放入缓存，所以此次读请求不进入队列
                return;
            }
        }
        //请求路由。将对应的数据路由到内存队列中
        ArrayBlockingQueue<Request> blockingQueue =  getRouteQueue(request.getId());
        //将请求放入此队列中
        try {
            blockingQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据grade的Id的hash值取模，获取此数据需要存放的队列位置
     * @param gradeId
     * @return
     */
    private ArrayBlockingQueue<Request> getRouteQueue(Integer gradeId){
        RequestQueue queues = RequestQueue.getInstance();

        String key = String.valueOf(gradeId);
        int h;
        int hash = (key == null) ? 0 : ( h = key.hashCode()) ^ (h >>> 16);
        //对hash值取模 获取到存放到队列的index
        int index = (queues.getQueuueSize()-1) & hash ;
        return queues.getQueueByIndex(index);
    }
}
