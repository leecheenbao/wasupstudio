package com.wasupstudio.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wasupstudio.exception.BusinessError;
import com.wasupstudio.exception.CommonError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVo<T> {
    private String code;
    private String message;
    private T data;

    public ResponseVo(BusinessError errorCode, String message, T data) {

        this.code = errorCode.getCode();
        if (StringUtils.isNotBlank(message)) {
            this.message = message;
        } else {
            this.message = errorCode.getMsg();
        }
        this.data = data;
    }

    public static <T> ResponseVo<T> ok() {
        return new ResponseVo<>(CommonError.SUCCESS, null, null);
    }

    public static <T> ResponseVo<T> ok(T data) {
        return new ResponseVo<>(CommonError.SUCCESS, null, data);
    }

    public static <T> ResponseVo<T> error(CommonError errorCode) {
        return new ResponseVo<>(errorCode, null, null);
    }
    public static <T> ResponseVo<T> error(CommonError errorCode, String message) {
        return new ResponseVo<>(errorCode, message, null);
    }
}
