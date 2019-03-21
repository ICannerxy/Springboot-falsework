package com.byavs.frame.dao.model;

import com.byavs.frame.core.base.entity.BaseModel;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

/**
 * Created by MyBatis Generator on 2019/03/21 11:10:25.
 * Table Name: user_admin_fun_perm
 * Table Desc: 管理员功能和权限关系表
*/
public class UserAdminFunPerm extends BaseModel {
    /**
     * 功能id
     */
    @Size(max = 32, message = "功能id长度不能超过32")
    private String funId;

    /**
     * 权限id
     */
    @Size(max = 32, message = "权限id长度不能超过32")
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