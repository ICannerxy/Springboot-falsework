package com.byavs.frame.core.base.constants;

/**
 * Created by qibin.long on 2017/4/14.
 */
public enum LogCatalog {

    SYS(-1, "system error"),
    APP(-2, "application error"),
    UPLOAD(-3, "file upload error"),
    SPRING_DATA_BIND(-4, "spring data bind error");

    private int code;
    private String value;

    private LogCatalog(int code, String value) {
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
