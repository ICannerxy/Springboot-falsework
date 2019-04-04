package com.byavs.frame.core.mybatis.cache;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;

import java.util.Set;


public interface EnhancedCacheManager {
    /**
     * 更新指定的StatementId集合与之关联的二级缓存
     *
     * @param statements
     */
    void clearStatementCache(Set<String> statements);

    /**
     * 将StatementId对应的Cache对象和CacheKey缓存起来
     *
     * @param statementId
     * @param cache
     * @param cacheKey
     */
    void saveStatementCache(String statementId, Cache cache, CacheKey cacheKey);
}
