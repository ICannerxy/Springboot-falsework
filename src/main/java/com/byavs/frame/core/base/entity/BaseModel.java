package com.byavs.frame.core.base.entity;



import com.byavs.frame.core.base.context.AppContext;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qibin.long on 2017/4/19.
 */
public class BaseModel implements Serializable {
    /**
     * 唯一标识
     */
    private String id;

    /**
     * 归属人
     */
    private String ownerId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人编号
     */
    private String creatorId;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改人编号
     */
    private String modifierId;

    /**
     * 版本号
     */
    private Integer recVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId == null ? null : modifierId.trim();
    }

    public Integer getRecVersion() {
        return recVersion;
    }

    public void setRecVersion(Integer recVersion) {
        this.recVersion = recVersion;
    }

    /**
     * 插入数据初始化
     */
    public void setDataForInsert() {
        LoginUserContext userContext = AppContext.getUserContext();
        //主键
        this.setId(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
        //拥有者
        this.setOwnerId(userContext.getOrgCode());
        //创建人
        this.setCreator(userContext.getNickName());
        //创建人ID
        this.setCreatorId(userContext.getUserName());
        //创建时间
        this.setCreateTime(new Date());
        //修改人
        this.setModifier(this.getCreator());
        //修改人ID
        this.setModifierId(this.getCreatorId());
        //修改时间
        this.setModifyTime(this.getCreateTime());
        //版本号
        this.setRecVersion(0);
    }

    /**
     * 修改数据初始化
     */
    public void setDataForUpdate() {
        LoginUserContext userContext = AppContext.getUserContext();
        //主键
        this.setId(null);
        //拥有者，修改时不修改
        this.setOwnerId(null);
        //创建人，修改时不修改
        this.setCreator(null);
        //创建人ID，修改时不修改
        this.setCreatorId(null);
        //创建时间，修改时不修改
        this.setCreateTime(null);
        //修改人
        this.setModifier(userContext.getNickName());
        //修改人ID
        this.setModifierId(userContext.getUserName());
        //修改时间
        this.setModifyTime(new Date());
    }
}