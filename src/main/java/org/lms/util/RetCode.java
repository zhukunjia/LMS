package org.lms.util;

import lombok.Getter;

@Getter
public enum RetCode {

    SUCCESS("0000", "success"),
    SERVER_ERROR("0001", "server error"),
    AUTH_ERROR("0002", "auth error"),
    NO_PERMISSION("0003", "no permission"),
    PARAM_ERROR("0004", "param error"),
    BUSINESS_ERROR("0005", "business error");

    private final String code;
    private final String msg;

    RetCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }



}
