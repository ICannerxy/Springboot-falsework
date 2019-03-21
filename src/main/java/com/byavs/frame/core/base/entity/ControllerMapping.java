package com.byavs.frame.core.base.entity;

/**
 * Created by qibin.long on 2017/6/1.
 */
public class ControllerMapping {
    private String url;
    private String fullName;
    private String description;
    private String httpMethod;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        return String.format("'%s','%s','%s','%s'", url, httpMethod, fullName, description);
    }
}