package com.springboot.model.employee;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.springboot.model.BasePageForm;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 员工档案
 * </p>
 *
 * @author zjq
 * @since 2020-04-22
 */
public class DaYgda extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
    private String ygbh;
    /**
     * 员工名称
     */
    private String ygmc;
    /**
     * 用户密码
     */
    private String yhmm;
    /**
     * 结点编号
     */
    private String jdbh;
    /**
     * 所属部门
     */
    private String ssbm;
    /**
     * 性别
     */
    private String xb;
    /**
     * 学历
     */
    private String xl;
    /**
     * 职务
     */
    private String zw;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 住址
     */
    private String zz;
    /**
     * 身份证号
     */
    private String sfzh;
    /**
     * 业务组
     */
    private String ywz;
    /**
     * 入职时间
     */
    private Date rzsj;
    /**
     * 当前状态
     */
    private String dqzt;
    /**
     * 简拼
     */
    private String jp;
    /**
     * 备注信息
     */
    private String bzxx;
    /**
     * 用户标志
     */
    private String yhbz;
    /**
     * 生日
     */
    private Date ygsr;
    /**
     * 最大折扣率
     */
    private Double zdzkl;
    /**
     * 部门授权
     */
    private String bmsq;
    /**
     * 客户授权
     */
    private String khsq;
    /**
     * 供应商授权
     */
    private String gyssq;
    /**
     * 品牌授权
     */
    private String ppsq;
    /**
     * 员工授权
     */
    private String ygsq;
    /**
     * 前台机器码
     */
    private String qtjqm;
    /**
     * 后台机器码
     */
    private String htjqm;
    /**
     * 权限组
     */
    private String qxz;

    @TableField(exist=false)
    private String token;
    @TableField(exist=false)
    private String uuid;
    @TableField(exist=false)
    private String bmmc;


    public String getYgbh() {
        return ygbh;
    }

    public void setYgbh(String ygbh) {
        this.ygbh = ygbh;
    }

    public String getYgmc() {
        return ygmc;
    }

    public void setYgmc(String ygmc) {
        this.ygmc = ygmc;
    }

    public String getYhmm() {
        return yhmm;
    }

    public void setYhmm(String yhmm) {
        this.yhmm = yhmm;
    }

    public String getJdbh() {
        return jdbh;
    }

    public void setJdbh(String jdbh) {
        this.jdbh = jdbh;
    }

    public String getSsbm() {
        return ssbm;
    }

    public void setSsbm(String ssbm) {
        this.ssbm = ssbm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getZw() {
        return zw;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getZz() {
        return zz;
    }

    public void setZz(String zz) {
        this.zz = zz;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getYwz() {
        return ywz;
    }

    public void setYwz(String ywz) {
        this.ywz = ywz;
    }

    public Date getRzsj() {
        return rzsj;
    }

    public void setRzsj(Date rzsj) {
        this.rzsj = rzsj;
    }

    public String getDqzt() {
        return dqzt;
    }

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    public String getYhbz() {
        return yhbz;
    }

    public void setYhbz(String yhbz) {
        this.yhbz = yhbz;
    }

    public Date getYgsr() {
        return ygsr;
    }

    public void setYgsr(Date ygsr) {
        this.ygsr = ygsr;
    }

    public Double getZdzkl() {
        return zdzkl;
    }

    public void setZdzkl(Double zdzkl) {
        this.zdzkl = zdzkl;
    }

    public String getBmsq() {
        return bmsq;
    }

    public void setBmsq(String bmsq) {
        this.bmsq = bmsq;
    }

    public String getKhsq() {
        return khsq;
    }

    public void setKhsq(String khsq) {
        this.khsq = khsq;
    }

    public String getGyssq() {
        return gyssq;
    }

    public void setGyssq(String gyssq) {
        this.gyssq = gyssq;
    }

    public String getPpsq() {
        return ppsq;
    }

    public void setPpsq(String ppsq) {
        this.ppsq = ppsq;
    }

    public String getYgsq() {
        return ygsq;
    }

    public void setYgsq(String ygsq) {
        this.ygsq = ygsq;
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

    @Override
    public String toString() {
        return "DaYgda{" +
        ", ygbh=" + ygbh +
        ", ygmc=" + ygmc +
        ", yhmm=" + yhmm +
        ", jdbh=" + jdbh +
        ", ssbm=" + ssbm +
        ", xb=" + xb +
        ", xl=" + xl +
        ", zw=" + zw +
        ", lxdh=" + lxdh +
        ", zz=" + zz +
        ", sfzh=" + sfzh +
        ", ywz=" + ywz +
        ", rzsj=" + rzsj +
        ", dqzt=" + dqzt +
        ", jp=" + jp +
        ", bzxx=" + bzxx +
        ", yhbz=" + yhbz +
        ", ygsr=" + ygsr +
        ", zdzkl=" + zdzkl +
        ", bmsq=" + bmsq +
        ", khsq=" + khsq +
        ", gyssq=" + gyssq +
        ", ppsq=" + ppsq +
        ", ygsq=" + ygsq +
        ", qtjqm=" + qtjqm +
        ", htjqm=" + htjqm +
        ", qxz=" + qxz +
        "}";
    }
}
