package org.lms.exception;

import lombok.extern.slf4j.Slf4j;
import org.lms.util.RetCode;
import org.lms.util.ServiceData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LmsException.class)
    public ServiceData<Void> handleLmsException(LmsException e) {
        return ServiceData.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ServiceData<Void> handleException(Exception e) {
        return ServiceData.fail(RetCode.SERVER_ERROR.getCode(), e.getMessage());
    }
}
