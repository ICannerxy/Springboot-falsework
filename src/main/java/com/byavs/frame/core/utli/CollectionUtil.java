package com.byavs.frame.core.utli;

import java.util.Collection;

/**
 * Created by zemo.wang on 2018/1/26.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty() || collection.size() == 0;
    }
}
