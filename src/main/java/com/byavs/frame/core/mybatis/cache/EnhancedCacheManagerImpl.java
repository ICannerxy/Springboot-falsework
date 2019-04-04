package com.byavs.frame.core.mybatis.cache;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qibin.long
 */
public class EnhancedCacheManagerImpl implements EnhancedCacheManager {
    /**
     * system logger
     */
    private static final transient Logger logger = LoggerFactory.getLogger(EnhancedCacheManagerImpl.class);
    /**
     * 缓存清理配置
     */
    private Map<String, Set<String>> config = new HashMap<String, Set<String>>();
    /**
     * 缓存所有查询语句对应的cacheKey
     */
    private Map<String, Set<CacheKey>> statementCacheKeys = new ConcurrentHashMap<String, Set<CacheKey>>();
    /**
     * 缓存所有查询语句的cache对象
     */
    private Map<String, Cache> statmentCacheMap = new ConcurrentHashMap<String, Cache>();

    public EnhancedCacheManagerImpl() {
    }

    /**
     * 缓存配置文件
     *
     * @param configLocations
     */
    public void setConfigLocations(Resource[] configLocations) throws IOException {
        if (configLocations == null || configLocations.length == 0) {
            return;
        }
        for (Resource resource : configLocations) {
            if (resource == null) {
                continue;
            }
            if (!resource.exists()) {
                throw new FileNotFoundException(String.format("%s is not exists.", resource));
            }
            try {
                String cacheConfig = IOUtils.toString(resource.getInputStream(), "utf-8");
                List<RefreshMybatisCache> list = JSONArray.parseArray(cacheConfig, RefreshMybatisCache.class);
                if (list == null || list.isEmpty()) {
                    continue;
                }
                for (RefreshMybatisCache refreshMybatisCache : list) {
                    Set<String> clear = new HashSet<>(refreshMybatisCache.getQuerySql());
                    if (refreshMybatisCache.getUpdateSql() == null || refreshMybatisCache.getUpdateSql().isEmpty() || clear == null || clear.isEmpty()) {
                        continue;
                    }
                    for (String key : refreshMybatisCache.getUpdateSql()) {
                        if (this.config.containsKey(key)) {
                            throw new RuntimeException(String.format("mybatis refresh cache settings [%s]: updateSql [%s] already exists", resource.getURI(), key));
                        } else {
                            this.config.put(key, clear);
                        }
                    }
                }
                logger.trace("mybatis enhanced cache config has initialized.");
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse mapping resource: \'" + resource.getURI() + "\'", e);
            }
        }
    }

    /**
     * 更新指定的statementId集合与之关联的二级缓存
     *
     * @param statements
     */
    @Override
    public void clearStatementCache(final Set<String> statements) {
        for (String statement : statements) {
            if (!config.containsKey(statement)) {
                continue;
            }
            Set<String> relatedStatements = config.get(statement);
            for (String clearStatement : relatedStatements) {
                if (!statmentCacheMap.containsKey(clearStatement) || !statementCacheKeys.containsKey(clearStatement)) {
                    continue;
                }
                //清理关联的查询语句对应的所有cacheKey缓存
                Cache cache = statmentCacheMap.get(clearStatement);
                Set<CacheKey> cacheKeys = statementCacheKeys.get(clearStatement);
                for (CacheKey cacheKey : cacheKeys) {
                    logger.trace(String.format("clear statement cache [{}.{}.{}]", statement, clearStatement, cacheKey));
                    cache.removeObject(cacheKey);
                }
                //清空缓存的查询语句对应的cacheKey
                statementCacheKeys.remove(clearStatement);
            }
        }
    }

    /**
     * 将StatementId对应的Cache对象和CacheKey缓存起来
     *
     * @param statementId
     * @param cache
     * @param cacheKey
     */
    @Override
    public void saveStatementCache(String statementId, Cache cache, CacheKey cacheKey) {
        //记录查询语句对应的缓存管理器
        if (!statmentCacheMap.containsKey(statementId) || statmentCacheMap.get(statementId) == null) {
            statmentCacheMap.put(statementId, cache);
        }
        //记录查询语句对应的缓存key
        if (statementCacheKeys.get(statementId) == null) {
            statementCacheKeys.put(statementId, new HashSet<CacheKey>());
        }
        statementCacheKeys.get(statementId).add(cacheKey);
    }
}

