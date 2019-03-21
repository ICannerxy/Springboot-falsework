package com.byavs.frame.core.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Created by zemo.wang on 2017/7/26.
 */
public class MapperInterfacePlugin extends PluginAdapter {
    //父接口
    private String rootInterface;

    private boolean isOverwrite = false;

    private boolean isInsertBatch = false;

    private ShellCallback shellCallback = null;

    public MapperInterfacePlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public void setProperties(Properties properties) {
        rootInterface = properties.getProperty("rootInterface");
        String isOverwrite = properties.getProperty("isOverwrite");
        if (stringHasValue(isOverwrite))
            this.isOverwrite = Boolean.parseBoolean(isOverwrite);
        String isInsertBatch = properties.getProperty("isInsertBatch");
        if (stringHasValue(isInsertBatch))
            this.isInsertBatch = Boolean.parseBoolean(isInsertBatch);
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //model类型
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        //导入model类型
        interfaze.addImportedType(modelType);
        //父接口
        if (stringHasValue(rootInterface)) {
            //example类型
            FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
            //父接口类型（包+类）
            FullyQualifiedJavaType superInterfaceType = new FullyQualifiedJavaType(rootInterface);
            //获取父接口的名称
            String superName = superInterfaceType.getShortName();
            //父接口类型（类）
            FullyQualifiedJavaType superType = new FullyQualifiedJavaType(superName);
            //继承父接口
            interfaze.addSuperInterface(superType);
            //导入类型
            interfaze.addImportedType(exampleType);
            interfaze.addImportedType(superInterfaceType);
            //添加泛型
            superType.addTypeArgument(modelType);
            superType.addTypeArgument(exampleType);
        }
        if (isInsertBatch) {
            String objectName = modelType.getShortName();//对象名称
            generateInsetBatchMethod(interfaze, objectName, "insertBatch");
            generateInsetBatchMethod(interfaze, objectName, "insertBatchSelective");
        }
        //是否覆盖已有文件
        if (isOverwrite) {
            return true;
        }

        //获取mapper接口的名称
        String mapperName = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName();
        //获取javaClientGenerator的配置
        JavaClientGeneratorConfiguration configuration = this.context.getJavaClientGeneratorConfiguration();
        try {
            File mapperDir = shellCallback.getDirectory(configuration.getTargetProject(), configuration.getTargetPackage());
            File mapperFile = new File(mapperDir, String.format("%s.java", mapperName));
            //文件存在
            if (mapperFile.exists()) {
                return false;
            }
        } catch (ShellException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 自定义方法
     *
     * @param interfaze  接口
     * @param objectName model名称
     * @param methodName 方法名称
     */
    private void generateInsetBatchMethod(Interface interfaze, String objectName, String methodName) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        String utilList = "java.util.List";
        //导入类型
        interfaze.addImportedType(new FullyQualifiedJavaType(utilList));
        String builder = new StringBuilder(utilList).append("<").append(objectName).append(">").toString();
        Method method = new Method();
        method.setName(methodName);
        if (methodName.equals("insertBatch")) {
            method.addParameter(new Parameter(new FullyQualifiedJavaType(builder), "list"));
        } else {
            method.addParameter(new Parameter(new FullyQualifiedJavaType(builder), "list", "@Param (\"list\")"));
            method.addParameter(new Parameter(new FullyQualifiedJavaType(objectName), "record", "@Param(\"record\")"));
        }
        method.setReturnType(new FullyQualifiedJavaType("int"));
        interfaze.addMethod(method);
    }
}
