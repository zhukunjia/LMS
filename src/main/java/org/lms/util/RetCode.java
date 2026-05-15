package org.lms.util;

import lombok.Getter;

@Getter
public enum RetCode {

    /**
     * 成功
     */
    SUCCESS("0000", "success"),
    /**
     * 服务器错误
     */
    SERVER_ERROR("0001", "server error"),
    /**
     * 未认证
     */
    AUTH_ERROR("0002", "auth error"),
    /**
     * 无权限
     */
    NO_PERMISSION("0003", "no permission"),
    /**
     * 参数错误
     */
    PARAM_ERROR("0004", "param error"),
    /**
     * 业务异常
     */
    BUSINESS_ERROR("0005", "business error");

    private final String code;
    private final String msg;

    RetCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }



}
