package org.lms.exception;

import lombok.extern.slf4j.Slf4j;
import org.lms.util.RetCode;
import org.lms.util.ServiceData;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LmsException.class)
    public ServiceData<Void> handleLmsException(LmsException e) {
        return ServiceData.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ServiceData<Map<String, String>> handleBindException(BindException e) {
        return bindingResultToResponse(e.getBindingResult());
    }

    @ExceptionHandler(Exception.class)
    public ServiceData<Void> handleException(Exception e) {
        log.error("occur error.", e);
        return ServiceData.fail(RetCode.SERVER_ERROR.getCode(), RetCode.SERVER_ERROR.getMsg());
    }

    private ServiceData<Map<String, String>> bindingResultToResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : bindingResult.getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        String msg = formatFieldErrors(errors);
        log.debug("Validation failed: {}", msg);
        return ServiceData.fail(RetCode.PARAM_ERROR.getCode(), msg, errors);
    }

    private static String formatFieldErrors(Map<String, String> errors) {
        return errors.entrySet().stream()
                .map(en -> en.getKey() + ": " + en.getValue())
                .collect(Collectors.joining("; "));
    }
}
