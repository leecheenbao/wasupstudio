package com.wasupstudio.model;

import com.wasupstudio.enums.ResultCode;

/**
 * 统一API响应结果封装
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result(){
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message=resultCode.getMessage();
    }

    public Result(ResultCode resultCode,Object data) {
        this.code = resultCode.getCode();
        this.message=resultCode.getMessage();
        this.data=data;
    }




    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message=resultCode.getMessage();
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

}
