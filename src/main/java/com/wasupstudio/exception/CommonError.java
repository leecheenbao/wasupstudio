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
    PRODUCT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "000009", "該商品數量不足"),
    TASK_INVALID(HttpStatus.BAD_REQUEST, "000010", "無效的任務"),
    SCRIPT_NOT_EXISTED(HttpStatus.BAD_REQUEST, "000011", "腳本不存在"),
    FILE_NOT_EXISTED(HttpStatus.BAD_REQUEST, "000012", "檔案不存在"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "000013", "沒有權限觀看"),
    CANNOT_DELETE(HttpStatus.BAD_REQUEST, "000014", "無法刪除"),
    TASK_NOT_EXISTED(HttpStatus.BAD_REQUEST, "000015", "任務不存在"),
    FILE_TYPE_ERROR(HttpStatus.BAD_REQUEST, "000016", "檔案類型錯誤"),
    FILE_SIZE_ERROR(HttpStatus.BAD_REQUEST, "000017", "檔案大小超過限制(30M)"),
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

