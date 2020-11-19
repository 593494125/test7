package com.springboot.model.finance;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 财务销货费用单
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public class CwNewXhfyd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 凭证号
     */
    private String pzh;
    /**
     * 日期
     */
    private Date rq;
    /**
     * 付款方式
     */
    private String fkfs;
    /**
     * 来源单号
     */
    private String lydh;
    /**
     * 部门编号
     */
    private String bmbh;
    /**
     * 客户编号
     */
    private String khbh;
    /**
     * 账户编号
     */
    private String zhbz;
    /**
     * 费用账号
     */
    private String fyzh;
    /**
     * 预留字段
     */
    private String fyxz;
    /**
     * 备注信息
     */
    private String bzxx;
    /**
     * 收费科目
     */
    private String sfkm;
    /**
     * 金额
     */
    private Double je;
    /**
     * 上单结余
     */
    private Double sdjy;
    /**
     * 累计余额
     */
    private Double ljye;
    /**
     * 说明
     */
    private String sm;
    /**
     * 记账标志
     */
    private String jzbz;
    /**
     * 记账时间
     */
    private Date jzsj;
    /**
     * 结账人
     */
    private String jzr;
    /**
     * 制单人
     */
    private String zdr;
    /**
     * 部门上单结余
     */
    private Double bmsdjy;
    /**
     * 部门累计余额
     */
    private Double bmljye;
    /**
     * 业务员
     */
    private String ywy;
    /**
     * 开单时间
     */
    private Date kdsj;

    @TableField(exist = false)
    private String bmmc;//部门名称
    @TableField(exist = false)
    private String khmc;//客户名称
    public String getPzh() {
        return pzh;
    }

    public void setPzh(String pzh) {
        this.pzh = pzh;
    }

    public Date getRq() {
        return rq;
    }

    public void setRq(Date rq) {
        this.rq = rq;
    }

    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public String getKhbh() {
        return khbh;
    }

    public void setKhbh(String khbh) {
        this.khbh = khbh;
    }

    public String getZhbz() {
        return zhbz;
    }

    public void setZhbz(String zhbz) {
        this.zhbz = zhbz;
    }

    public String getFyzh() {
        return fyzh;
    }

    public void setFyzh(String fyzh) {
        this.fyzh = fyzh;
    }

    public String getFyxz() {
        return fyxz;
    }

    public void setFyxz(String fyxz) {
        this.fyxz = fyxz;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    public String getSfkm() {
        return sfkm;
    }

    public void setSfkm(String sfkm) {
        this.sfkm = sfkm;
    }

    public Double getJe() {
        return je;
    }

    public void setJe(Double je) {
        this.je = je;
    }

    public Double getSdjy() {
        return sdjy;
    }

    public void setSdjy(Double sdjy) {
        this.sdjy = sdjy;
    }

    public Double getLjye() {
        return ljye;
    }

    public void setLjye(Double ljye) {
        this.ljye = ljye;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getJzbz() {
        return jzbz;
    }

    public void setJzbz(String jzbz) {
        this.jzbz = jzbz;
    }

    public Date getJzsj() {
        return jzsj;
    }

    public void setJzsj(Date jzsj) {
        this.jzsj = jzsj;
    }

    public String getJzr() {
        return jzr;
    }

    public void setJzr(String jzr) {
        this.jzr = jzr;
    }

    public String getZdr() {
        return zdr;
    }

    public void setZdr(String zdr) {
        this.zdr = zdr;
    }

    public Double getBmsdjy() {
        return bmsdjy;
    }

    public void setBmsdjy(Double bmsdjy) {
        this.bmsdjy = bmsdjy;
    }

    public Double getBmljye() {
        return bmljye;
    }

    public void setBmljye(Double bmljye) {
        this.bmljye = bmljye;
    }

    public String getYwy() {
        return ywy;
    }

    public void setYwy(String ywy) {
        this.ywy = ywy;
    }

    public Date getKdsj() {
        return kdsj;
    }

    public void setKdsj(Date kdsj) {
        this.kdsj = kdsj;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getKhmc() {
        return khmc;
    }

    public void setKhmc(String khmc) {
        this.khmc = khmc;
    }
}
