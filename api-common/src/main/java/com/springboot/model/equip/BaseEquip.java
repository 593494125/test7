package com.springboot.model.equip;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.BasePageForm;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-04-23
 */
public class BaseEquip extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String uuid;
    private String equipType;
    private String equipManufacturer;
    private String userName;
    private String orgId;
    private String createTime;
    private String updateTime;
    private String sn;


    @TableField(exist=false)
    private String orgName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getEquipManufacturer() {
        return equipManufacturer;
    }

    public void setEquipManufacturer(String equipManufacturer) {
        this.equipManufacturer = equipManufacturer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "BaseEquip{" +
        ", id=" + id +
        ", uuid=" + uuid +
        ", equipType=" + equipType +
        ", equipManufacturer=" + equipManufacturer +
        ", userName=" + userName +
        ", orgId=" + orgId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
