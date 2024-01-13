package com.wasupstudio.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonError implements BusinessError {
    SUCCESS(HttpStatus.OK, "000000", "Success"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "000001", "系統異常，請稍後重試"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "000002", "Not Found"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "000003", "Bad Request"),
    PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "000004", "參數錯誤"),
    DATA_EXISTED(HttpStatus.BAD_REQUEST, "000005", "資料已存在"),
    DATA_NOT_EXISTED(HttpStatus.BAD_REQUEST, "000006", "資料不存在"),

    MEMBER_NOT_EXISTED(HttpStatus.BAD_REQUEST, "000007", "會員不存在"),
    PRODUCT_NOT_EXISTED(HttpStatus.BAD_REQUEST, "000008", "商品不存在"),
    PRODUCT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "000009", "該商品數量不足")
    ;

    private final String code;

    private final String msg;

    private final HttpStatus status;

    CommonError(HttpStatus status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCategory() {
        return "common";
    }
}

