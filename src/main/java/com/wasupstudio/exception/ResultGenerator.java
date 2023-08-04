package com.wasupstudio.exception;

import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.model.Result;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAILED_MESSAGE = "FAILED";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genSuccessResult(String message, Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message)
                .setData(data);
    }

    public static Result genSuccessResult(int code, String message, Object data) {
        return new Result()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static Result genFailResult(int code, String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
    }

    public static Result genFailResult(int code, String message, Object data) {
        return new Result()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

    public static Result genError() {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(DEFAULT_FAILED_MESSAGE);
    }
}
