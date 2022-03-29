package com.jyw.learn.service;


import com.jyw.learn.myrequest.Request;

/**
 * 请求异步处理
 */
public interface IRequestAsyncProcessService {
    void process(Request request);
}
