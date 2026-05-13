package org.lms.util;

import lombok.Data;

@Data
public class ServiceData<T> {
    private String code;
    private String msg;
    private T data;

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

}
