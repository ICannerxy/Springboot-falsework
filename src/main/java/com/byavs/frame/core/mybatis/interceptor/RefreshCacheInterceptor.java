package com.byavs.frame.core.mybatis.interceptor;


import com.byavs.frame.core.mybatis.cache.EnhancedCacheManager;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


@Intercepts(value = {
        @Signature(args = {MappedStatement.class, Object.class}, method = "update", type = Executor.class),
        @Signature(args = {boolean.class}, method = "commit", type = Executor.class),
        @Signature(args = {boolean.class}, method = "rollback", type = Executor.class),
        @Signature(args = {boolean.class}, method = "close", type = Executor.class)
})
public class RefreshCacheInterceptor implements Interceptor {
    private static ThreadLocal<Set<String>> updateStatementList = new ThreadLocal<Set<String>>() {
        @Override
        protected Set<String> initialValue() {
            return new HashSet<String>();
        }
    };

    private EnhancedCacheManager cachingManager;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String name = invocation.getMethod().getName();
        Object result = null;
        if ("update".equals(name)) {
            result = this.processUpdate(invocation);
        } else if ("commit".equals(name)) {
            result = this.processCommit(invocation);
        } else if ("rollback".equals(name)) {
            result = this.processRollback(invocation);
        } else if ("close".equals(name)) {
            result = this.processClose(invocation);
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    protected Object processUpdate(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        updateStatementList.get().add(mappedStatement.getId());
        return result;
    }

    protected Object processCommit(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        refreshCache();
        return result;
    }

    protected Object processRollback(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        return result;
    }

    protected Object processClose(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        boolean forceRollback = (Boolean) invocation.getArgs()[0];
        if (!forceRollback) {
            refreshCache();
        }
        return result;
    }

    private void refreshCache() {
        if (cachingManager != null) {
            cachingManager.clearStatementCache(updateStatementList.get());
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }


    public void setCachingManager(EnhancedCacheManager cachingManager) {
        this.cachingManager = cachingManager;
    }
}
