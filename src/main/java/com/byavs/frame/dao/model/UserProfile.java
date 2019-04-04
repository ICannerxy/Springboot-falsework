package com.byavs.frame.dao.model;

import com.byavs.frame.core.entity.BaseModel;
import java.util.Date;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

/**
 * Created by MyBatis Generator on 2019/04/04 13:27:14.
 * Table Name: user_profile
 * Table Desc: 用户表
*/
public class UserProfile extends BaseModel {

    /**
     * 头像
     */
    @Size(max = 255, message = "头像长度不能超过255")
    private String avatar;

    /**
     * 账号
     */
    @Size(max = 45, message = "账号长度不能超过45")
    private String account;

    /**
     * 密码
     */
    @Size(max = 45, message = "密码长度不能超过45")
    private String password;

    /**
     * md5密码盐
     */
    @Size(max = 45, message = "md5密码盐长度不能超过45")
    private String salt;

    /**
     * 名字
     */
    @Size(max = 45, message = "名字长度不能超过45")
    private String name;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    @Digits(integer = 10, fraction = 0, message = "性别精度失真")
    private Integer sex;

    /**
     * 电子邮件
     */
    @Size(max = 45, message = "电子邮件长度不能超过45")
    private String email;

    /**
     * 电话
     */
    @Size(max = 45, message = "电话长度不能超过45")
    private String phone;

    /**
     * 角色id
     */
    @Size(max = 255, message = "角色id长度不能超过255")
    private String roleid;

    /**
     * 部门id
     */
    @Digits(integer = 10, fraction = 0, message = "部门id精度失真")
    private Integer deptid;

    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    @Digits(integer = 10, fraction = 0, message = "状态(1：启用  2：冻结  3：删除）精度失真")
    private Integer status;

    /**
     * 保留字段
     */
    @Digits(integer = 10, fraction = 0, message = "保留字段精度失真")
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