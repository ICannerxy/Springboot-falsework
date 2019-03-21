package com.byavs.frame.core.base.context;


import com.byavs.frame.core.base.entity.LoginUserContext;

/**
 * Created by qibin.long on 2017/4/17.
 */
public final class AppContext {
    private static final ThreadLocal<LoginUserContext> USER_INFO_THREAD_LOCAL = new InheritableThreadLocal<LoginUserContext>();

    private AppContext() {
    }

    /**
     * 返回登录用户上下文
     *
     * @return
     */
    public static LoginUserContext getUserContext() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    /**
     * 设置登录用户上下文
     *
     * @param obj
     */
    public static void setLoginUserContext(final Object obj) {
        if (obj instanceof LoginUserContext) {
            USER_INFO_THREAD_LOCAL.set((LoginUserContext) obj);
        }
    }

    /**
     * 模拟登录用户上下文
     *
     * @param
     */
    public static void mockUserContext(MockUserContext mockUserContext) {
        USER_INFO_THREAD_LOCAL.set(mockUserContext);
    }
}