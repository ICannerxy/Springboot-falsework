package com.byavs.frame.core.base.context;

import java.io.Serializable;

/**
 * Created by zemo.wang on 2017/8/21.
 */
public class NoticeContext implements Serializable {
    /**
     * 通知标志
     */
    private boolean noticeService = false;
    /**
     * 通知内容
     */
    private String noticeInfo;
    /**
     * 通知发起人
     */
    private String noticeCreator;
    /**
     * 停止服务标志
     */
    private boolean stopService = false;
    /**
     * 停止服务内容
     */
    private String stopInfo;
    /**
     * 停止服务发起人
     */
    private String stopCreator;
    /**
     * 用户会话消息推送Key
     */
    public static final String SESSION_PUSH_KEY = "SINGLE_USER_SESSION_PUSH_MESSAGE_KEY";
    /**
     * 用户会话通知key
     */
    public static final String NOTICE_READ_MESSAGE = "SESSION_NOTICE_READ_MESSAGE";

    public NoticeContext() {
    }

    public boolean isNoticeService() {
        return noticeService;
    }

    public void setNoticeService(boolean noticeService) {
        this.noticeService = noticeService;
    }

    public String getNoticeInfo() {
        return noticeInfo;
    }

    public void setNoticeInfo(String noticeInfo) {
        this.noticeInfo = noticeInfo;
    }

    public boolean isStopService() {
        return stopService;
    }

    public void setStopService(boolean stopService) {
        this.stopService = stopService;
    }

    public String getStopInfo() {
        return stopInfo;
    }

    public void setStopInfo(String stopInfo) {
        this.stopInfo = stopInfo;
    }

    public String getNoticeCreator() {
        return noticeCreator;
    }

    public void setNoticeCreator(String noticeCreator) {
        this.noticeCreator = noticeCreator;
    }

    public String getStopCreator() {
        return stopCreator;
    }

    public void setStopCreator(String stopCreator) {
        this.stopCreator = stopCreator;
    }
}
