package com.byavs.frame.core.base.entity;

import java.io.Serializable;

/**
 * Created by qibin.long on 2018/9/11.
 */
public abstract class LoginUserContext implements Serializable {
    /**
     * 用户ID
     */
    protected String userId;
    /**
     * 用户名
     */
    protected String userName;
    /**
     * 昵称
     */
    protected String nickName;
    /**
     * 组织机构代码
     */
    protected String orgCode;
    /**
     * 组织机构名称
     */
    protected String orgName;

    /**
     * 用户角色,多个用逗号分隔
     */
    protected String role;
    /**
     * 扩展数据
     */
    protected Object extData;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public Object getExtData() {
        return extData;
    }

    public String getRole() {
        return role;
    }
}
