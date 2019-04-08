package com.byavs.frame.domain.constant;

/**
 * @author XuYang
 * @description:
 * @date 2019/4/8  10:00
 */
public enum ManagerStatus {

    OK(1, "启用"),
    FREEZED(2, "冻结"),
    DELETED(3, "删除");

    private int code;
    private String value;

    ManagerStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
