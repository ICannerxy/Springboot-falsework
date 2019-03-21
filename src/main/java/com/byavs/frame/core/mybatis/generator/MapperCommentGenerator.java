
package com.byavs.frame.core.mybatis.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by qibin.long on 2017/4/17.
 */
public class MapperCommentGenerator implements CommentGenerator {
    private String beginningDelimiter = "";
    private String endingDelimiter = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public MapperCommentGenerator() {
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement(String.format("<!-- @mbg.generated %s.-->", getDateString())));
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        String beginningDelimiter = properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(beginningDelimiter)) {
            this.beginningDelimiter = beginningDelimiter;
        }

        String endingDelimiter = properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(endingDelimiter)) {
            this.endingDelimiter = endingDelimiter;
        }

    }

    public String getDelimiterName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(this.beginningDelimiter);
        nameBuilder.append(name);
        nameBuilder.append(this.endingDelimiter);
        return nameBuilder.toString();
    }

    private String getDateString() {
        return dateFormat.format(new Date());
    }

    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder column = new StringBuilder();
            column.append(" * ");
            column.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(column.toString());
            field.addJavaDocLine(" */");
        }

        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.setSqlServerRemark(introspectedTable);
        //转义字段备注双引号
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            if (StringUtility.stringHasValue(column.getRemarks())) {
                column.setRemarks(StringUtility.escapeStringForJava(column.getRemarks().trim()));
            } else {
                column.setRemarks("");
            }
        }
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(String.format(" * Created by MyBatis Generator on %s.", getDateString())); //$NON-NLS-1$
        topLevelClass.addJavaDocLine(String.format(" * Table Name: %s", introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        topLevelClass.addJavaDocLine(String.format(" * Table Desc: %s", StringUtility.stringHasValue(introspectedTable.getRemarks()) ? introspectedTable.getRemarks() : ""));
        topLevelClass.addJavaDocLine("*/");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }

    private void setSqlServerRemark(IntrospectedTable introspectedTable) {
        Connection conn = null;
        String driverClass = introspectedTable.getContext().getJdbcConnectionConfiguration().getDriverClass().toLowerCase();
        String connectionUrl = introspectedTable.getContext().getJdbcConnectionConfiguration().getConnectionURL().toLowerCase();
        if (!driverClass.contains("sqlserver") && !connectionUrl.contains("sqlserver")) {
            return;
        }
        String userId = introspectedTable.getContext().getJdbcConnectionConfiguration().getUserId();
        String password = introspectedTable.getContext().getJdbcConnectionConfiguration().getPassword();
        //表名
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName().toLowerCase();
        String sql = "SELECT a.name AS table_name\n" +
                "\t,(\n" +
                "\t\tSELECT convert(VARCHAR(100), t1.value)\n" +
                "\t\tFROM sys.extended_properties t1\n" +
                "\t\tWHERE t1.major_id = a.object_id\n" +
                "\t\t\tAND t1.minor_id = '0'\n" +
                "\t\t) AS table_remark\n" +
                "\t,b.name AS column_name\n" +
                "\t,convert(VARCHAR(100), c.VALUE) AS column_remark\n" +
                "FROM sys.tables a\n" +
                "LEFT JOIN sys.columns b ON b.object_id = a.object_id\n" +
                "LEFT JOIN sys.extended_properties c ON c.major_id = b.object_id\n" +
                "\tAND c.minor_id = b.column_id\n" +
                "WHERE lower(a.name) = ? \n";
        try {
            conn = DriverManager.getConnection(connectionUrl, userId, password);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tableName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                introspectedTable.setRemarks(rs.getString("table_remark"));
                String columnName = rs.getString("column_name");
                String columnRemark = rs.getString("column_remark");
                for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
                    if (columnName.equalsIgnoreCase(column.getActualColumnName())) {
                        column.setRemarks(columnRemark);
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }
    }
}