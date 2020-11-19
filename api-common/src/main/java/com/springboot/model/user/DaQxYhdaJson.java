package com.springboot.model.user;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.posparm.YwPosParm;

import java.io.Serializable;

public class DaQxYhdaJson implements Serializable {

    private String yhbh;
    private String ygbh;
    private String yhmc;
    private String ygmc;
    private String bmbh;
    private String bmmc;
    private String qtjqm;
    private String htjqm;
    private String token;
    private String uuid;
    private String expiretime;
    private YwPosParm ywPosParm;

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    public String getYgbh() {
        return ygbh;
    }

    public void setYgbh(String ygbh) {
        this.ygbh = ygbh;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getYgmc() {
        return ygmc;
    }

    public void setYgmc(String ygmc) {
        this.ygmc = ygmc;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getQtjqm() {
        return qtjqm;
    }

    public void setQtjqm(String qtjqm) {
        this.qtjqm = qtjqm;
    }

    public String getHtjqm() {
        return htjqm;
    }

    public void setHtjqm(String htjqm) {
        this.htjqm = htjqm;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime;
    }

    public YwPosParm getYwPosParm() {
        return ywPosParm;
    }

    public void setYwPosParm(YwPosParm ywPosParm) {
        this.ywPosParm = ywPosParm;
    }
}
