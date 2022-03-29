package com.jyw.learn.vo;

import lombok.Data;

/**
 * 相应返回
 */
@Data
public class Response {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    private Object msg;
    private String status;

    public Response(Object msg, String status) {
        this.msg = msg;
        this.status = status;
    }

    public Response() {
    }
}
