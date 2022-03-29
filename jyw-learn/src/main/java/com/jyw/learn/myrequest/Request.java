package com.jyw.learn.myrequest;

public interface Request {
    void process();
    Integer getId();
    boolean idForceRefresh();
}
