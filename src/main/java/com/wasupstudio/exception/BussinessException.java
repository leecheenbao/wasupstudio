package com.wasupstudio.exception;


import com.wasupstudio.enums.ResultCode;

/**
 * 业务异常
 */
public class BussinessException extends RuntimeException {

    private int code;

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BussinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public BussinessException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage());
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return this.code;
    }
}
