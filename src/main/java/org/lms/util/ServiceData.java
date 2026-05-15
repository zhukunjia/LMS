package org.lms.util;

import lombok.Data;

@Data
public class ServiceData<T> {
    /**
     * 返回码
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public static <T> ServiceData<T> success() {
        ServiceData<T> serviceData = new ServiceData<>();
        serviceData.setCode(RetCode.SUCCESS.getCode());
        serviceData.setMsg(RetCode.SUCCESS.getCode());

        return serviceData;
    }

    public static <T> ServiceData<T> success(T data) {
        ServiceData<T> serviceData = new ServiceData<>();
        serviceData.setCode(RetCode.SUCCESS.getCode());
        serviceData.setMsg(RetCode.SUCCESS.getCode());
        serviceData.setData(data);

        return serviceData;
    }

    public static <T> ServiceData<T> fail(String code, String msg) {
        ServiceData<T> serviceData = new ServiceData<>();
        serviceData.setCode(code);
        serviceData.setMsg(msg);
        serviceData.setData(null);
        return serviceData;
    }

    public static <T> ServiceData<T> fail(RetCode retCode) {
        return fail(retCode.getCode(), retCode.getMsg());
    }

    public static <T> ServiceData<T> fail(String code, String msg, T data) {
        ServiceData<T> serviceData = new ServiceData<>();
        serviceData.setCode(code);
        serviceData.setMsg(msg);
        serviceData.setData(data);
        return serviceData;
    }

}
