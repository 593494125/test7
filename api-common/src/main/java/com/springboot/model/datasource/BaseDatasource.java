package com.springboot.model.datasource;

import com.springboot.model.BasePageForm;

import java.io.Serializable;

public class BaseDatasource extends BasePageForm implements Serializable {

    private String id;
    private String url;
    private String userName;
    private String passWord;
    private String code;
    private String type;//1：SQLServer
    private String driverClassName;
    private String orgId;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseDatasource() {
    }

    public BaseDatasource(String id, String url, String userName, String passWord, String code, String type, String orgId) {
        this.id = id;
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        this.code = code;
        this.type = type;
        this.orgId = orgId;
    }
}
