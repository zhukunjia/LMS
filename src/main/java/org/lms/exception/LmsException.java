package org.lms.exception;

import lombok.Getter;

@Getter
public class LmsException extends RuntimeException {

    private final String code;

    public LmsException(String code, String message) {
        super(message);
        this.code = code;
    }
}
