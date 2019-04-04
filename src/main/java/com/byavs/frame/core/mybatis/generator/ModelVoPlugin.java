package com.byavs.frame.core.mybatis.generator;

/**
 * Created by qibin.long on 2017/4/17.
 */

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class ModelVoPlugin extends PluginAdapter {
    //vo目录
    private String modelVoTargetDir;
    //vo包名
    private String modelVoTargetPackage;
    //已有文件是否覆盖，默认不覆盖
    boolean isOverwrite = false;
    //swaggerUI的注解
    private String apiModelProperty;

    private ShellCallback shellCallback = null;

    public ModelVoPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
    }

    /**
     * 设置属性
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String isOverwrite = properties.getProperty("isOverwrite");
        if (stringHasValue(isOverwrite)) {
            this.isOverwrite = Boolean.parseBoolean(isOverwrite);
        }

        String apiModelProperty = properties.getProperty("apiModelProperty");
        if (stringHasValue(apiModelProperty)) {
            this.apiModelProperty = apiModelProperty;
        }
    }

    /**
     * 校验属性
     *
     * @param warnings
     * @return
     */
    @Override
    public boolean validate(List<String> warnings) {
        modelVoTargetDir = properties.getProperty("modelVoTargetDir");
        boolean validVo = stringHasValue(modelVoTargetDir);
        if (validVo) {
            modelVoTargetPackage = properties.getProperty("modelVoTargetPackage");
            validVo = stringHasValue(modelVoTargetPackage);
        }
        return validVo;
    }

    /**
     * 生成modelVo文件
     *
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        //格式化java代码
        JavaFormatter javaFormatter = context.getJavaFormatter();
        //生成的Java文件的编码
        String encoding = context.getProperty("javaFileEncoding");
        //生成文件的名称
        StringBuilder modelFileName = new StringBuilder();
        modelFileName.append(modelVoTargetPackage).append(".").append(introspectedTable.getFullyQualifiedTable().getDomainObjectName()).append("Vo");
        //获取所有列
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        //创建类
        TopLevelClass topLevelClass = new TopLevelClass(modelFileName.toString());
        //设置类权限
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        //父类
        boolean isRootClass = false;
        String rootClass = this.context.getJavaModelGeneratorConfiguration().getProperty("rootClass");
        if (stringHasValue(rootClass)) {
            FullyQualifiedJavaType rootCalssType = new FullyQualifiedJavaType(rootClass);
            topLevelClass.setSuperClass(rootCalssType);
            topLevelClass.addImportedType(rootCalssType);
            isRootClass = true;
        }
        //swaggerUI的注解
        if (stringHasValue(apiModelProperty)) {
            topLevelClass.addImportedType(new FullyQualifiedJavaType(apiModelProperty));
        }
        //循环遍历列
        for (IntrospectedColumn column : columnList) {
            //父类有该字段就跳过
            if (isRootClass && RootClassInfo.getInstance(rootClass, null).containsProperty(column))
                continue;
            //生成字段
            Field field = JavaBeansUtil.getJavaBeansField(column, this.context, introspectedTable);
            if (modelFieldGenerated(field, topLevelClass, column, introspectedTable, ModelClassType.BASE_RECORD)) {

                if (stringHasValue(apiModelProperty)) {
                    field.addJavaDocLine(String.format("@ApiModelProperty(value = \"%s\")", column.getRemarks()));
                }
                topLevelClass.addField(field);
                topLevelClass.addImportedType(field.getType());
            }
            //生成getter方法
            Method methodGetter = JavaBeansUtil.getJavaBeansGetter(column, this.context, introspectedTable);
            if (modelGetterMethodGenerated(methodGetter, topLevelClass, column, introspectedTable, ModelClassType.BASE_RECORD)) {
                topLevelClass.addMethod(methodGetter);
            }
            //生成setter方法
            Method methodSetter = JavaBeansUtil.getJavaBeansSetter(column, this.context, introspectedTable);
            if (modelSetterMethodGenerated(methodSetter, topLevelClass, column, introspectedTable, ModelClassType.BASE_RECORD)) {
                topLevelClass.addMethod(methodSetter);
            }
        }
        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
        if (isOverwrite) {
            mapperJavaFiles.add(new GeneratedJavaFile(topLevelClass, modelVoTargetDir, encoding, javaFormatter));
        } else {
            try {
                File modelVoDir = shellCallback.getDirectory(modelVoTargetDir, modelVoTargetPackage);
                File modelVoFile = new File(modelVoDir, String.format("%sVo.java", introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
                //文件不存在
                if (!modelVoFile.exists()) {
                    mapperJavaFiles.add(new GeneratedJavaFile(topLevelClass, modelVoTargetDir, encoding, javaFormatter));
                }
            } catch (ShellException e) {
                throw new RuntimeException(e);
            }
        }
        return mapperJavaFiles;
    }
}
