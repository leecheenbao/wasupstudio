package com.wasupstudio.exception;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final BusinessError error;

    public BusinessException(BusinessError error, String message) {
        super(message);
        this.error = error;
    }
    public BusinessException(BusinessError error) {
        super(error.getMsg());
        this.error = error;
    }
}
