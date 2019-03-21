package com.byavs.frame.domain.vo;

import com.byavs.frame.core.base.entity.BaseModel;
import io.swagger.annotations.ApiModelProperty;

public class UserAdminFunPermVo extends BaseModel {
    /**
     * 功能id
     */
    @ApiModelProperty(value = "功能id")
    private String funId;

    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    private String permissionId;

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId == null ? null : funId.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }
}