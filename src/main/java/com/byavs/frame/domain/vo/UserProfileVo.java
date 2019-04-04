package com.byavs.frame.domain.vo;

import com.byavs.frame.core.entity.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class UserProfileVo extends BaseModel {


    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * md5密码盐
     */
    @ApiModelProperty(value = "md5密码盐")
    private String salt;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字")
    private String name;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    @ApiModelProperty(value = "性别（1：男 2：女）")
    private Integer sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private String roleid;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer deptid;

    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    @ApiModelProperty(value = "状态(1：启用  2：冻结  3：删除）")
    private Integer status;

    /**
     * 保留字段
     */
    @ApiModelProperty(value = "保留字段")
    private Integer version;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}