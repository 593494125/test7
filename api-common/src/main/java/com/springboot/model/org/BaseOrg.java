package com.springboot.model.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.BasePageForm;

import java.io.Serializable;

public class BaseOrg extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String orgName;
    private String phone;//公司电话
    private String address;//公司地址
    private Integer state;//0：冻结1：激活
    private String path;
    private String createTime;
    private String createUser;
    private String createUserId;
    private String updateTime;
    private String updateUser;
    private String updateUserId;
    private Integer count;
    private String bigOrgName;
    private Integer isStartTmFangan;
    private String hostAddress;
    private String areaAddress;
    private String areaServiceUrl;

    @TableField(exist = false)
    private Integer equiptCount;
    @TableField(exist = false)
    private Integer sixCodeCount;
    @TableField(exist = false)
    private Integer sourceCount;
    @TableField(exist = false)
    private Integer redisSourceCount;
    @TableField(exist = false)
    private Integer orgHostCount;
    @TableField(exist = false)
    private String sixCode;
    @TableField(exist = false)
    private String key;//模糊查询使用
    @TableField(exist = false)
    private String redisId;//
    @TableField(exist = false)
    private String code;//数据库名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getEquiptCount() {
        return equiptCount;
    }

    public void setEquiptCount(Integer equiptCount) {
        this.equiptCount = equiptCount;
    }

    public Integer getSixCodeCount() {
        return sixCodeCount;
    }

    public void setSixCodeCount(Integer sixCodeCount) {
        this.sixCodeCount = sixCodeCount;
    }

    public Integer getSourceCount() {
        return sourceCount;
    }

    public void setSourceCount(Integer sourceCount) {
        this.sourceCount = sourceCount;
    }

    public String getBigOrgName() {
        return bigOrgName;
    }

    public void setBigOrgName(String bigOrgName) {
        this.bigOrgName = bigOrgName;
    }


    public Integer getIsStartTmFangan() {
        return isStartTmFangan;
    }

    public void setIsStartTmFangan(Integer isStartTmFangan) {
        this.isStartTmFangan = isStartTmFangan;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public Integer getRedisSourceCount() {
        return redisSourceCount;
    }

    public void setRedisSourceCount(Integer redisSourceCount) {
        this.redisSourceCount = redisSourceCount;
    }

    public Integer getOrgHostCount() {
        return orgHostCount;
    }

    public void setOrgHostCount(Integer orgHostCount) {
        this.orgHostCount = orgHostCount;
    }

    @Override
    public String getSixCode() {
        return sixCode;
    }

    @Override
    public void setSixCode(String sixCode) {
        this.sixCode = sixCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRedisId() {
        return redisId;
    }

    public void setRedisId(String redisId) {
        this.redisId = redisId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAreaAddress() {
        return areaAddress;
    }

    public void setAreaAddress(String areaAddress) {
        this.areaAddress = areaAddress;
    }

    public String getAreaServiceUrl() {
        return areaServiceUrl;
    }

    public void setAreaServiceUrl(String areaServiceUrl) {
        this.areaServiceUrl = areaServiceUrl;
    }

    public BaseOrg() {
        super();
    }

}
