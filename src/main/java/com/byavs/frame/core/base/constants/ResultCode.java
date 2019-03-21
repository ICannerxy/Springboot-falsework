package com.byavs.frame.core.base.constants;

/**
 * Created by qibin.long on 2017/4/14.
 */
public enum ResultCode {

    SUCCESS(0, "ok"),
    NOTICE(1, "notice"),
    ERROR(-1, "unknown exception");

    private int code;
    private String value;

    private ResultCode(int code, String value) {
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
