package com.byavs.frame.core.utli;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qibin.long on 2017/4/16.
 */
public final class BeanCopyUtil {
    /**
     * 将一个对象转换为指定类型对象
     *
     * @param obj    源对象
     * @param tClass 目标类型
     * @param <T>
     * @return
     */
    public static <T> T cloneObject(Object obj, Class<T> tClass) {
        if (obj == null) {
            return null;
        }
        T target = BeanUtils.instantiate(tClass);
        BeanUtils.copyProperties(obj, target);
        return target;

    }

    /**
     * 将一个List转换为指定类型List
     *
     * @param obj    源对象
     * @param tClass 目标类型
     * @param <T>
     * @return
     */
    public static <T> List<T> cloneObject(List obj, Class<T> tClass) {
        if (null == obj) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        Iterator iterator = obj.iterator();
        while (iterator.hasNext()) {
            T target = BeanUtils.instantiate(tClass);
            BeanUtils.copyProperties(iterator.next(), target);
            list.add(target);
        }
        return list;
    }
}
