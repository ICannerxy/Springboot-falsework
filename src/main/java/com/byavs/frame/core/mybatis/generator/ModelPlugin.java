package com.byavs.frame.core.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Created by zemo.wang on 2018/8/21.
 */
public class ModelPlugin extends PluginAdapter {
    // 验证对象是否不为null, 无法查检长度为0的字符串
    private String notNull = "javax.validation.constraints.NotNull";
    // 检查约束元素是否为NULL或者是EMPTY
    private String notEmpty = "org.hibernate.validator.constraints.NotEmpty";
    // 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内
    private String size = "javax.validation.constraints.Size";
    // 验证字符串是否是符合指定格式的数字，interger指定整数精度，fraction指定小数精度
    private String digits = "javax.validation.constraints.Digits";
    //  验证值是否小于等于最大指定小数值
    private String decimalMax = "javax.validation.constraints.DecimalMax";
    // 备注分隔符
    @NotNull(message = "")
    private String splitRemark = "（,\\(";
    // 字符串类型
    private String stringType = "java.lang.String";
    // BigDecimal类型
    private String BigDecimalType = "java.math.BigDecimal";
    // 数字集合
    private List<String> digitTypeList = new ArrayList<String>() {
        {
            add("java.lang.Float");
            add("java.lang.Double");
            add("java.lang.Integer");
            add("java.lang.Long");
        }
    };

    /**
     * 校验属性
     *
     * @param warnings
     * @return
     */
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 设置属性
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String notNull = properties.getProperty("notNull");
        if (stringHasValue(notNull)) {
            this.notNull = notNull;
        }
        String notEmpty = properties.getProperty("notEmpty");
        if (stringHasValue(notEmpty)) {
            this.notEmpty = notEmpty;
        }
        String size = properties.getProperty("size");
        if (stringHasValue(size)) {
            this.size = size;
        }
        String digits = properties.getProperty("digits");
        if (stringHasValue(digits)) {
            this.digits = digits;
        }
        String decimalMax = properties.getProperty("decimalMax");
        if (stringHasValue(decimalMax)) {
            this.decimalMax = decimalMax;
        }
        String splitRemark = properties.getProperty("splitRemark");
        if (stringHasValue(splitRemark)) {
            this.splitRemark = splitRemark;
        }
    }

    /**
     * 字段扩展
     *
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        // 获取备注
        String remark = getRemark(introspectedColumn);
        if (!stringHasValue(remark)) {
            return true;
        }
        // 导入类型
        /*topLevelClass.addImportedType(new FullyQualifiedJavaType(notNull));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(notEmpty));*/
        topLevelClass.addImportedType(new FullyQualifiedJavaType(size));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(digits));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(decimalMax));
        // 获取字段Java类型
        FullyQualifiedJavaType qualifiedJavaType = introspectedColumn.getFullyQualifiedJavaType();
        String javaType = String.format("%s.%s", qualifiedJavaType.getPackageName(), qualifiedJavaType.getShortName());
        /*// 必填字段
        if (!introspectedColumn.isNullable()) {
            if (stringType.equals(javaType)) {
                field.addJavaDocLine(String.format("@NotEmpty(message = \"%s必填\")", remark));
            } else {
                field.addJavaDocLine(String.format("@NotNull(message = \"%s必填\")", remark));
            }
        }*/
        int length = introspectedColumn.getLength();
        int scale = introspectedColumn.getScale();
        if (stringType.equals(javaType)) {
            // 字段长度
            field.addJavaDocLine(String.format("@Size(max = %s, message = \"%s长度不能超过%s\")", length, remark, length));
        } else if (digitTypeList.contains(javaType)) {
            // 数字整数和小数长度
            field.addJavaDocLine(String.format("@Digits(integer = %s, fraction = %s, message = \"%s精度失真\")", length - scale, scale, remark));
        } else if (BigDecimalType.equals(javaType)) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length - scale; i++) {
                builder.append(0);
            }
            String bigDecimal = String.format("1%s", builder.toString());
            field.addJavaDocLine(String.format("@DecimalMax(value = \"%s\" ,message = \"%s精度失真\")", bigDecimal, remark));
        }
        return true;
    }

    /**
     * 获取备注（不包含枚举或其他信息）
     *
     * @param introspectedColumn
     * @return
     */
    private String getRemark(IntrospectedColumn introspectedColumn) {
        String remark = introspectedColumn.getRemarks();
        if (!stringHasValue(remark) || !stringHasValue(splitRemark)) {
            return remark;
        }
        String[] splitRemarks = splitRemark.split(",");
        for (String split : splitRemarks) {
            if (split.contains(split)) {
                return remark.split(split)[0];
            }
        }
        return remark;
    }
}