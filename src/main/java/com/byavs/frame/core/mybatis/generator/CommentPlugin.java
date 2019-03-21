package com.byavs.frame.core.mybatis.generator;

/**
 * Created by qibin.long on 2017/4/17.
 */

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

public class CommentPlugin extends PluginAdapter {
    private Set<String> mappers = new HashSet();
    private boolean caseSensitive = false;
    private String beginningDelimiter = "";
    private String endingDelimiter = "";
    private String schema;
    private CommentGeneratorConfiguration commentCfg;

    public CommentPlugin() {
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.commentCfg = new CommentGeneratorConfiguration();
        this.commentCfg.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
        context.setCommentGeneratorConfiguration(this.commentCfg);
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);

        String var7 = this.properties.getProperty("caseSensitive");
        if (StringUtility.stringHasValue(var7)) {
            this.caseSensitive = var7.equalsIgnoreCase("TRUE");
        }

        String var8 = this.properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(var8)) {
            this.beginningDelimiter = var8;
        }

        this.commentCfg.addProperty("beginningDelimiter", this.beginningDelimiter);
        String var9 = this.properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(var9)) {
            this.endingDelimiter = var9;
        }

        this.commentCfg.addProperty("endingDelimiter", this.endingDelimiter);
        schema = this.properties.getProperty("schema");
        if (StringUtility.stringHasValue(schema)) {
            this.schema = schema;
        }
    }

    public String getDelimiterName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        if (StringUtility.stringHasValue(this.schema)) {
            nameBuilder.append(this.schema);
            nameBuilder.append(".");
        }

        nameBuilder.append(this.beginningDelimiter);
        nameBuilder.append(name);
        nameBuilder.append(this.endingDelimiter);
        return nameBuilder.toString();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        Iterator var5 = this.mappers.iterator();

        while (var5.hasNext()) {
            String mapper = (String) var5.next();
            interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
        }

        interfaze.addImportedType(entityType);
        return true;
    }

    private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        /*
        topLevelClass.addImportedType("javax.persistence.*");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        if (StringUtility.stringContainsSpace(tableName)) {
            tableName = this.context.getBeginningDelimiter() + tableName + this.context.getEndingDelimiter();
        }

        if (this.caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + this.getDelimiterName(tableName) + "\")");
        } else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + this.getDelimiterName(tableName) + "\")");
        } else if (StringUtility.stringHasValue(this.schema) || StringUtility.stringHasValue(this.beginningDelimiter) || StringUtility.stringHasValue(this.endingDelimiter)) {
            topLevelClass.addAnnotation("@Table(name = \"" + this.getDelimiterName(tableName) + "\")");
        }
        */
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return false;
    }
}
