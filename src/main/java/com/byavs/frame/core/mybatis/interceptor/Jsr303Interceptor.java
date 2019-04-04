package com.byavs.frame.core.mybatis.interceptor;

import com.byavs.frame.core.exception.ApplicationException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created by zemo.wang on 2018/9/5.
 */
@Intercepts(value = {
        @Signature(args = {MappedStatement.class, Object.class}, method = "update", type = Executor.class)
})
public class Jsr303Interceptor implements Interceptor {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取参数
        Object[] args = invocation.getArgs();
        // 获取第一个参数
        MappedStatement ms = (MappedStatement) args[0];
        // 获取参数注解
        Annotation[][] argAnnotations = getParameterAnnotations(ms);
        // 是否含有valid注解
        boolean hasValid = hasValid(argAnnotations);
        // 操作类型
        boolean type = sqlCommandType(ms);
        if (hasValid && type) {
            // 验证参数
            validateEntity(args[1]);
        }
        return invocation.proceed();
    }

    /**
     * 验证参数
     *
     * @param obj
     */
    private void validateEntity(Object obj) {
        // 记录错误信息
        StringBuilder builder = new StringBuilder();
        Iterable iterable;
        if (obj instanceof Iterable) {
            iterable = (Iterable) obj;
        } else {
            iterable = Arrays.asList(obj);
        }
        Iterator entity = iterable.iterator();
        while (entity.hasNext()) {
            int length = builder.length();
            Set<ConstraintViolation<Object>> violations = validator.validate(entity.next());
            Iterator<ConstraintViolation<Object>> iterator = violations.iterator();
            while (iterator.hasNext()) {
                builder.append("\n").append(iterator.next().getMessage());
            }
            if (builder.length() > length) {
                builder.append("\n");
            }
        }
        if (builder.length() > 0) {
            throw new ApplicationException(builder.substring(1));
        }
    }

    /**
     * 操作类型
     *
     * @param ms
     */
    private boolean sqlCommandType(MappedStatement ms) {
        // 获取sql操作类型
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        // insert 或 update 操作的参数进行验证
        if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            return true;
        }
        return false;
    }

    /**
     * 是否含有valid注解
     *
     * @param argAnnotations
     * @return
     */
    private boolean hasValid(Annotation[][] argAnnotations) {
        if (null == argAnnotations) {
            return false;
        }
        for (int i = 0; i < argAnnotations.length; i++) {
            for (Annotation annotation : argAnnotations[i]) {
                if (Valid.class.isInstance(annotation)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取参数注解
     *
     * @param ms
     * @return
     * @throws ClassNotFoundException
     */
    private Annotation[][] getParameterAnnotations(MappedStatement ms) throws ClassNotFoundException {
        // 包名 + 类名 + 方法名
        String id = ms.getId();
        // 获取 . 最后的角标
        int lastIndex = id.lastIndexOf(".");
        // 截取包名+类名
        String className = id.substring(0, lastIndex);
        // 当前线程类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 反射类
        Class clazz = Class.forName(className, true, classLoader);
        // 获取所有方法
        Method[] methods = clazz.getMethods();
        // 截取方法名
        String methodName = id.substring(lastIndex + 1);
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                // 方法中的参数注解
                return method.getParameterAnnotations();
            }
        }
        return null;
    }

    /**
     * 是否含有 javax.validation.Valid 注解
     *
     * @param annotations
     * @return
     */
    private boolean hasValid(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (Valid.class.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
