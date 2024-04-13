package com.wasupstudio.exception;

import org.springframework.http.HttpStatus;

public interface BusinessError {

    String getCode();

    String getMsg();

    HttpStatus getStatus();

    String getCategory();
}
