package com.byavs.frame.core.utli;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by qibin.long on 2018/9/20.
 */
public final class PropertyPlaceholderUtil {
    /**
     * 读取指定路径属性
     *
     * @return
     */
    public static Properties loadProperties(String... locations) {
        try {
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            propertiesFactoryBean.setSingleton(false);
            propertiesFactoryBean.setFileEncoding("utf-8");
            propertiesFactoryBean.setIgnoreResourceNotFound(true);
            propertiesFactoryBean.setLocations(ResourceResolverUtil.getResources(locations));
            return propertiesFactoryBean.getObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 读取指定资源属性
     *
     * @return
     */
    public static Properties loadProperties(Resource... locations) {
        try {
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            propertiesFactoryBean.setSingleton(false);
            propertiesFactoryBean.setFileEncoding("utf-8");
            propertiesFactoryBean.setIgnoreResourceNotFound(true);
            propertiesFactoryBean.setLocations(locations);
            return propertiesFactoryBean.getObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 反射实体属性值
     *
     * @param prefix
     * @param obj
     */
    public static void reflecFieldValueFromProperties(Properties properties, String prefix, Class<?> clazz, Object obj) {
        Field[] fields = clazz.getDeclaredFields();
        String fieldValue = "";
        for (Field field : fields) {
            fieldValue = properties.getProperty(prefix + field.getName());
            if (null == fieldValue) {
                continue;
            }
            Object value = null;
            if (int.class.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType())) {
                value = Integer.valueOf(fieldValue);
            } else if (boolean.class.isAssignableFrom(field.getType()) || Boolean.class.isAssignableFrom(field.getType())) {
                value = Boolean.valueOf(fieldValue);
            } else if (long.class.isAssignableFrom(field.getType()) || Long.class.isAssignableFrom(field.getType())) {
                value = Long.valueOf(fieldValue);
            } else if (String.class.isAssignableFrom(field.getType())) {
                value = fieldValue.trim();
            }
            if (null == value) {
                continue;
            }
            try {
                field.setAccessible(true);
                field.set(obj, value);
            } catch (IllegalAccessException ex) {
            }
        }
    }
}
