package com.springboot.model.user;


import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.BasePageForm;
import com.springboot.model.BaseSixCode;


public class BaseUser extends BasePageForm {
    private String id;

    private String name;
    private String userName;
    private String passWord;
    private Integer state;//0：冻结1：激活
    private String remarks;
    private String createTime;
    private String createUser;
    private String createUserId;
    private String updateTime;
    private String updateUser;
    private String updateUserId;
    private String orgId;

    @TableField(exist=false)
    private String token;

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BaseUser() {
    }

    public BaseUser(String id, String name, String userName, String passWord, Integer state, String remarks, String createTime, String createUser, String createUserId, String updateTime, String updateUser, String updateUserId, String orgId) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.passWord = passWord;
        this.state = state;
        this.remarks = remarks;
        this.createTime = createTime;
        this.createUser = createUser;
        this.createUserId = createUserId;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.updateUserId = updateUserId;
        this.orgId = orgId;
    }
}
