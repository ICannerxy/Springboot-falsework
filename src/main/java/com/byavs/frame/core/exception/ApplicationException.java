package com.byavs.frame.core.exception;


import com.byavs.frame.core.constants.ResultCode;

/**
 * Created by qibin.long on 2017/4/14.
 */
public class ApplicationException extends RuntimeException {
    private int code = ResultCode.ERROR.getCode();

    private String msg;

    private Object data;

    public ApplicationException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApplicationException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ApplicationException(int code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApplicationException(String msg, Throwable throwable) {
        super(throwable);
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
