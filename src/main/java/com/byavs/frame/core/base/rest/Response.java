package com.byavs.frame.core.base.rest;



import com.byavs.frame.core.base.constants.ResultCode;

import java.io.Serializable;

/**
 * Created by qibin.long on 2017/4/14.
 * for RestApi Response entity
 */
public class Response implements Serializable {

    /**
     * 默认返回代码 0
     */
    private String ret = String.valueOf(ResultCode.SUCCESS.getCode());
    /**
     * 默认返回 ok
     */
    private String msg = ResultCode.SUCCESS.getValue();
    /**
     * 自定义对象
     */
    private Object data;

    public Response() {
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
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


    public static Response success( ) {
        Response response = new Response();
        response.ret = String.valueOf(ResultCode.SUCCESS.getCode());
        response.msg = ResultCode.SUCCESS.getValue();
        return response;

    }


    public static Response success(Object data) {
        Response response = new Response();
        response.ret = String.valueOf(ResultCode.SUCCESS.getCode());
        response.msg = ResultCode.SUCCESS.getValue();
        response.data = data;
        return response;

    }


    public static Response success(String ret, String msg ) {
        Response response = new Response();
        response.ret = ret;
        response.msg = msg;
        return response;
    }


    public static Response success(String ret, String msg, Object data) {
        Response response = new Response();
        response.ret = ret;
        response.msg = msg;
        response.data = data;
        return response;

    }

    public static Response failure() {
        Response response = new Response();
        response.ret = String.valueOf(ResultCode.ERROR.getCode());
        response.msg = ResultCode.ERROR.getValue();
        return response;
    }

    public static Response failure(String msg) {
        Response response = new Response();
        response.ret = String.valueOf(ResultCode.ERROR.getCode());
        response.msg = msg;
        return response;
    }
      //操作失败

    public static Response failure(String ret, String msg) {
        Response response = new Response();
        response.ret = ret;
        response.msg = msg;
        return response;

    }

    public static Response failure(String ret, String msg, Object data) {
        Response response = new Response();
        response.ret = ret;
        response.msg = msg;
        response.data = data;
        return response;
    }
}