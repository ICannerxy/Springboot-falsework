package com.byavs.frame.core.mybatis.cache;

import java.util.List;

/**
 * Created by zemo.wang on 2018/6/29.
 */
public class RefreshMybatisCache {
    private String comment;
    private List<String> updateSql;

    private List<String> querySql;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getUpdateSql() {
        return updateSql;
    }

    public void setUpdateSql(List<String> updateSql) {
        this.updateSql = updateSql;
    }

    public List<String> getQuerySql() {
        return querySql;
    }

    public void setQuerySql(List<String> querySql) {
        this.querySql = querySql;
    }
}
