package com.byavs.frame.core.mybatis.ehcache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by qibin.long on 2018/8/2.
 */
public class EhcacheCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(EhcacheCache.class);

    /**
     * The cache manager reference.
     */
    private static final Map<ClassLoader, CacheManager> currentContextCacheManger =
            new ConcurrentHashMap<ClassLoader, CacheManager>(1);

    /**
     * The cache id (namespace)
     */
    private String id;

    /**
     * The cache instance
     */
    protected Ehcache cache;
    /**
     * The {@code ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public EhcacheCache() {
    }

    /**
     * @param id
     */
    public EhcacheCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id.toLowerCase();
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        CacheManager cm = currentContextCacheManger.get(ccl);
        if (cm == null) {
            throw new RuntimeException("mybatis ehcache not init.");
        }
        logger.trace("init mybatis ehcache name {}", cm.getName());
        if (!cm.cacheExists(this.id)) {
            cm.addCache(this.id);
        }
        this.cache = cm.getEhcache(this.id);
    }

    public static void initEhcacheCache(CacheManager cacheManager) {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        CacheManager cm = currentContextCacheManger.get(ccl);
        if (cm != null) {
            throw new RuntimeException(String.format("mybatis echache [%s] already init.", cm.getName()));
        }
        currentContextCacheManger.put(ccl, cacheManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        cache.removeAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(Object key) {
        logger.trace("mybatis ehcache name {}", cache.getCacheManager().getName());
        Element cachedElement = cache.get(key);
        if (cachedElement == null) {
            return null;
        }
        return cachedElement.getObjectValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return cache.getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putObject(Object key, Object value) {
        logger.trace("mybatis ehcache name {}", cache.getCacheManager().getName());
        cache.put(new Element(key, value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object removeObject(Object key) {
        logger.trace("mybatis ehcache name {}", cache.getCacheManager().getName());
        Object obj = getObject(key);
        cache.remove(key);
        return obj;
    }

    /**
     * {@inheritDoc}
     */
    public void unlock(Object key) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cache)) {
            return false;
        }

        Cache otherCache = (Cache) obj;
        return id.equals(otherCache.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "EHCache {"
                + id
                + "}";
    }

    // DYNAMIC PROPERTIES

    /**
     * Sets the time to idle for an element before it expires. Is only used if the element is not eternal.
     *
     * @param timeToIdleSeconds the default amount of time to live for an element from its last accessed or modified date
     */
    public void setTimeToIdleSeconds(long timeToIdleSeconds) {
        this.cache.getCacheConfiguration().setTimeToIdleSeconds(timeToIdleSeconds);
    }

    /**
     * Sets the time to idle for an element before it expires. Is only used if the element is not eternal.
     *
     * @param timeToLiveSeconds the default amount of time to live for an element from its creation date
     */
    public void setTimeToLiveSeconds(long timeToLiveSeconds) {
        this.cache.getCacheConfiguration().setTimeToLiveSeconds(timeToLiveSeconds);
    }

    /**
     * Sets the maximum objects to be held in memory (0 = no limit).
     *
     * @param maxEntriesLocalHeap maxElementsInMemory The maximum number of elements in memory, before they are evicted (0 == no limit)
     */
    public void setMaxEntriesLocalHeap(long maxEntriesLocalHeap) {
        this.cache.getCacheConfiguration().setMaxEntriesLocalHeap(maxEntriesLocalHeap);
    }

    /**
     * Sets the maximum number elements on Disk. 0 means unlimited.
     *
     * @param maxEntriesLocalDisk maxElementsOnDisk the maximum number of Elements to allow on the disk. 0 means unlimited.
     */
    public void setMaxEntriesLocalDisk(long maxEntriesLocalDisk) {
        this.cache.getCacheConfiguration().setMaxEntriesLocalDisk(maxEntriesLocalDisk);
    }

    /**
     * Sets the eviction policy. An invalid argument will set it to null.
     *
     * @param memoryStoreEvictionPolicy a String representation of the policy. One of "LRU", "LFU" or "FIFO".
     */
    public void setMemoryStoreEvictionPolicy(String memoryStoreEvictionPolicy) {
        this.cache.getCacheConfiguration().setMemoryStoreEvictionPolicy(memoryStoreEvictionPolicy);
    }
}