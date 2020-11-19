package com.springboot.model.user;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.posparm.YwPosParm;
import com.springboot.model.system.GnqxJson;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户档案
 * </p>
 *
 * @author zjq
 * @since 2020-06-17
 */
public class DaQxYhda implements Serializable {

    private static final long serialVersionUID = 1L;

    private String yhbh;
    private String yhmc;
    private String yhmm;
    private String bmbh;
    private String jdbh;
    private String dqbh;
    private String ygbh;
    private String bzxx;
    private String yhzw;
    private String khbh;
    private String wxh;
    private String qxzbz;
    private String qxzbh;
    private String qtjqm;
    private String htjqm;
    private String qxz;

    @TableField(exist=false)
    private String sixCode;
    @TableField(exist=false)
    private String token;
    @TableField(exist=false)
    private String uuid;
    @TableField(exist=false)
    private String bmmc;
    @TableField(exist=false)
    private String ygmc;
    @TableField(exist=false)
    private String expiretime;
    @TableField(exist=false)
    private String groupId;
    @TableField(exist=false)
    private YwPosParm ywPosParm;
    @TableField(exist=false)
    private Object daQjCsb;
    @TableField(exist=false)
    private List<GnqxJson> gnqxList;
    @TableField(exist=false)
    private Map<String,String> moKuaiAuth;

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getYhmm() {
        return yhmm;
    }

    public void setYhmm(String yhmm) {
        this.yhmm = yhmm;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public String getJdbh() {
        return jdbh;
    }

    public void setJdbh(String jdbh) {
        this.jdbh = jdbh;
    }

    public String getDqbh() {
        return dqbh;
    }

    public void setDqbh(String dqbh) {
        this.dqbh = dqbh;
    }

    public String getYgbh() {
        return ygbh;
    }

    public void setYgbh(String ygbh) {
        this.ygbh = ygbh;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    public String getYhzw() {
        return yhzw;
    }

    public void setYhzw(String yhzw) {
        this.yhzw = yhzw;
    }

    public String getKhbh() {
        return khbh;
    }

    public void setKhbh(String khbh) {
        this.khbh = khbh;
    }

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public String getQxzbz() {
        return qxzbz;
    }

    public void setQxzbz(String qxzbz) {
        this.qxzbz = qxzbz;
    }

    public String getQxzbh() {
        return qxzbh;
    }

    public void setQxzbh(String qxzbh) {
        this.qxzbh = qxzbh;
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

    public String getQxz() {
        return qxz;
    }

    public void setQxz(String qxz) {
        this.qxz = qxz;
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

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getYgmc() {
        return ygmc;
    }

    public void setYgmc(String ygmc) {
        this.ygmc = ygmc;
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


    public Object getDaQjCsb() {
        return daQjCsb;
    }

    public void setDaQjCsb(Object daQjCsb) {
        this.daQjCsb = daQjCsb;
    }

    public List<GnqxJson> getGnqxList() {
        return gnqxList;
    }

    public void setGnqxList(List<GnqxJson> gnqxList) {
        this.gnqxList = gnqxList;
    }

    public Map<String, String> getMoKuaiAuth() {
        return moKuaiAuth;
    }

    public void setMoKuaiAuth(Map<String, String> moKuaiAuth) {
        this.moKuaiAuth = moKuaiAuth;
    }

    public String getSixCode() {
        return sixCode;
    }

    public void setSixCode(String sixCode) {
        this.sixCode = sixCode;
    }
}
