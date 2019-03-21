package com.byavs.frame.core.base.context;


import com.byavs.frame.core.base.entity.LoginUserContext;

/**
 * Created by qibin.long on 2018/12/17.
 */
public final class MockUserContext extends LoginUserContext {
    public MockUserContext(String userId, String userName, String nickName, String orgCode, String orgName, String role, Object extData) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.role = role;
        this.extData = extData;
    }
}