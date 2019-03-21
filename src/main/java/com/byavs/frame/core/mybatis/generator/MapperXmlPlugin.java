package com.byavs.frame.core.mybatis.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Created by zemo.wang on 2017/7/27.
 */
public class MapperXmlPlugin extends PluginAdapter {
    //命名空间
    private String namespacePackage;

    @Override
    public boolean validate(List<String> list) {
        namespacePackage = properties.getProperty("namespacePackage");
        boolean isPack = stringHasValue(namespacePackage);
        return isPack;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        //修改命名空间
        introspectedTable.setMyBatis3FallbackSqlMapNamespace(String.format("%s.%s", namespacePackage, new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName()));
        super.initialized(introspectedTable);
    }

    /**
     * 生成额外的sql语句
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        //批量插入
        rootElement.addElement(getInsertBatchElement(introspectedTable));
        return true;
    }

    private Element getInsertBatchElement(IntrospectedTable introspectedTable) {
        //数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        //insert标签
        XmlElement insertBatchElement = new XmlElement("insert");
        //inser标签添加属性
        insertBatchElement.addAttribute(new Attribute("id", "insertBatch"));
        insertBatchElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        context.getCommentGenerator().addComment(insertBatchElement);
        //拼接insert语句
        StringBuilder insertBuilder = new StringBuilder();
        insertBuilder.append("insert into ").append(tableName).append(" (");
        //拼接values部分
        StringBuilder valuesBuilder = new StringBuilder();
        valuesBuilder.append("(");
        List<String> valuesClauses = new ArrayList<String>();
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn column = iter.next();
            //cannot set values on identity fields
            if (column.isIdentity())
                continue;
            insertBuilder.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
            valuesBuilder.append(MyBatis3FormattingUtilities.getParameterClause(column, "item."));
            if (iter.hasNext()) {
                insertBuilder.append(", ");
                valuesBuilder.append(", ");
            }
            if (valuesBuilder.length() > 80) {
                insertBatchElement.addElement(new TextElement(insertBuilder.toString()));
                insertBuilder.setLength(0);
                OutputUtilities.xmlIndent(insertBuilder, 1);

                valuesClauses.add(valuesBuilder.toString());
                valuesBuilder.setLength(0);
                OutputUtilities.xmlIndent(valuesBuilder, 1);
            }
        }
        insertBuilder.append(')');
        insertBatchElement.addElement(new TextElement(insertBuilder.toString()));

        valuesBuilder.append(')');
        valuesClauses.add(valuesBuilder.toString());
        //foreach标签
        XmlElement foreachElement = newForeachElement();
        for (String clause : valuesClauses) {
            foreachElement.addElement(new TextElement(clause));
        }
        insertBatchElement.addElement(new TextElement("values "));
        insertBatchElement.addElement(foreachElement);
        return insertBatchElement;
    }

    /**
     * if标签
     *
     * @param javaProperty
     * @return
     */
    private XmlElement newIfElement(String javaProperty) {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", String.format("record.%s != null", javaProperty)));
        return ifElement;
    }

    /**
     * trim标签
     *
     * @return
     */
    private XmlElement newTrimElement() {
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("prefix", "("));
        trimElement.addAttribute(new Attribute("suffix", ")"));
        trimElement.addAttribute(new Attribute("suffixOverrides", ","));
        return trimElement;
    }

    /**
     * foreach标签
     *
     * @return
     */
    private XmlElement newForeachElement() {
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        return foreachElement;
    }
}
