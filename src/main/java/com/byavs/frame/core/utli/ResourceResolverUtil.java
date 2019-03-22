package com.byavs.frame.core.utli;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by zemo.wang on 2018/8/1.
 */
public final class ResourceResolverUtil {

    /**
     * 解析多个资源配置
     *
     * @param locationPatterns
     * @return
     */
    public static Resource[] getResources(String... locationPatterns) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Assert.notNull(locationPatterns, "Location pattern must not be null");
        Set<Resource> result = new LinkedHashSet<Resource>(5);
        for (String locationPattern : locationPatterns) {
            Resource[] resources = resolver.getResources(locationPattern);
            if (null != resources && resources.length > 0) {
                result.addAll(Arrays.asList(resources));
            }
        }
        return result.toArray(new Resource[result.size()]);
    }

    /**
     * 过滤资源地址,只返回存在的路径
     *
     * @param locationPatterns
     * @return
     */
    public static Collection<String> filterExistsResources(String... locationPatterns) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Assert.notNull(locationPatterns, "Location pattern must not be null");
        Collection result = new HashSet<String>();
        for (String locationPattern : locationPatterns) {
            Resource[] resources = null;
            try {
                resources = resolver.getResources(locationPattern);
            } catch (FileNotFoundException ex) {
                continue;
            }
            if (null != resources && resources.length > 0) {
                result.add(locationPattern);
            }
        }
        return result;
    }
}
