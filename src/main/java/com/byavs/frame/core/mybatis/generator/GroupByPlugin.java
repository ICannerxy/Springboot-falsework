package com.byavs.frame.core.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Created by zemo.wang on 2018/9/25.
 */
public class GroupByPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 扩展example，添加排序
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List<Method> methodList = topLevelClass.getMethods();
        for (Method method : methodList) {
            if ("clear".equals(method.getName())) {
                method.addBodyLine("groupByClause = null;");
            }
        }
        FullyQualifiedJavaType stringType = new FullyQualifiedJavaType("java.lang.String");
        // 添加字段
        Field field = new Field("groupByClause", stringType);
        field.setVisibility(JavaVisibility.PROTECTED);
        topLevelClass.addField(field);
        // 添加setter方法
        Method setGroupByClause = new Method("setGroupByClause");
        // 添加可见性
        setGroupByClause.setVisibility(JavaVisibility.PUBLIC);
        // 添加返回类型
        setGroupByClause.setReturnType(new FullyQualifiedJavaType("void"));
        // 添加参数
        Parameter parameter = new Parameter(stringType, "groupByClause");
        setGroupByClause.addParameter(parameter);
        // 添加方法体
        setGroupByClause.addBodyLine("this.groupByClause = groupByClause;");
        topLevelClass.addMethod(setGroupByClause);
        // 添加getter方法
        Method getGroupByClause = new Method("getGroupByClause");
        // 添加可见性
        getGroupByClause.setVisibility(JavaVisibility.PUBLIC);
        // 添加返回类型
        getGroupByClause.setReturnType(stringType);
        // 添加方法体
        getGroupByClause.addBodyLine("return groupByClause;");
        topLevelClass.addMethod(getGroupByClause);
        return true;
    }

    /**
     * 扩展查询语句，添加排序
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "groupByClause != null"));
        ifElement.addElement(new TextElement("group by ${groupByClause}"));
        element.addElement(6, ifElement);
        return true;
    }
}
