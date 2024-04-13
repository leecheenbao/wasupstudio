package com.wasupstudio.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.wasupstudio.exception.BusinessException;
import com.wasupstudio.exception.CommonError;
import com.wasupstudio.model.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseVo<?>> missingServletRequestParameterError(MissingServletRequestParameterException e) {
        CommonError error = CommonError.PARAMETER_ERROR;
        return ResponseEntity.status(error.getStatus()).body(getLocalizedErrorResponse(error));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseVo<?>> missingServletRequestParameterError(MethodArgumentNotValidException e) {
        CommonError error = CommonError.PARAMETER_ERROR;
        return ResponseEntity.status(error.getStatus()).body(getLocalizedErrorResponse(e.getFieldError()));
    }

    private ResponseVo<?> getLocalizedErrorResponse(FieldError fieldError) {
        CommonError err = CommonError.PARAMETER_ERROR;
        String prefix = getSourceMessage(err.getCategory() + ".error." + err.getCode());
        if (StringUtils.isBlank(prefix)) {
            prefix = err.getMsg();
        }
        String message = prefix + "(" + fieldError.getField() + " " + fieldError.getDefaultMessage() + ")";
        return ResponseVo.error(err, message);
    }

    private ResponseVo<?> getLocalizedErrorResponse(CommonError err) {
        String message = getSourceMessage(err.getCategory() + ".error." + err.getCode());
        if (StringUtils.isBlank(message)) {
            message = err.getMsg();
        }
        return ResponseVo.error(err, message);
    }

    private String getSourceMessage(String key) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseVo<?>> authenticationCredentialsNotFoundError(HttpRequestMethodNotSupportedException e) {
        log.error("", e);
        CommonError error = CommonError.NOT_FOUND;
        return ResponseEntity.status(error.getStatus()).body(getLocalizedErrorResponse(error));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ResponseVo<?>> businessError(BusinessException e) {
        log.error("", e);
        CommonError error = (CommonError) e.getError();
        return ResponseEntity.status(error.getStatus()).body(getLocalizedErrorResponse(error));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = JsonParseException.class)
    public ResponseEntity<ResponseVo<?>> JsonParseError(JsonParseException e) {
        log.error("", e);
        CommonError error = CommonError.BAD_REQUEST;
        return ResponseEntity.status(error.getStatus()).body(getLocalizedErrorResponse(error));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseVo<?>> HttpMessageNotReadableError(HttpMessageNotReadableException e) {
        log.error("", e);
        CommonError error = CommonError.BAD_REQUEST;
        return ResponseEntity.status(error.getStatus()).body(getLocalizedErrorResponse(error));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseVo<?>> error(Throwable e) {
        log.error("", e);
        CommonError error = CommonError.SYSTEM_ERROR;
        return ResponseEntity.status(error.getStatus()).body(ResponseVo.error(error));
    }

}
