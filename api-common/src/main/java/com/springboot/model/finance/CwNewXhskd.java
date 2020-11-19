package com.springboot.model.finance;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 财务销货收款单
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public class CwNewXhskd implements Serializable {

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
     * 收款方式
     */
    private String skfs;
    /**
     * 部门编号
     */
    private String bmbh;
    /**
     * 客户编号
     */
    private String khbh;
    /**
     * 来源单号
     */
    private String lydh;
    /**
     * 账户编号
     */
    private String zhbz;
    /**
     * 费用账号
     */
    private String fyzh;
    /**
     * 收款类型
     */
    private String sklx;
    /**
     * 备注信息
     */
    private String bzxx;
    /**
     * 业务员
     */
    private String ywy;
    /**
     * 制单人
     */
    private String zdr;
    /**
     * 上单结余
     */
    private Double sdjy;
    /**
     * 本次应收货款
     */
    private Double bcyshk;
    /**
     * 代付运费
     */
    private Double dfyf;
    /**
     * 本次收款
     */
    private Double bcsk;
    /**
     * 优惠金额
     */
    private Double yhje;
    /**
     * 定金
     */
    private Double dj;
    /**
     * 保证金
     */
    private Double bzj;
    /**
     * 累计余额
     */
    private Double ljye;
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
     * 数量
     */
    private Double sl;
    /**
     * 部门上单结余
     */
    private Double bmsdjy;
    /**
     * 部门累计余额
     */
    private Double bmljye;
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

    public String getSkfs() {
        return skfs;
    }

    public void setSkfs(String skfs) {
        this.skfs = skfs;
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

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
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

    public String getSklx() {
        return sklx;
    }

    public void setSklx(String sklx) {
        this.sklx = sklx;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    public String getYwy() {
        return ywy;
    }

    public void setYwy(String ywy) {
        this.ywy = ywy;
    }

    public String getZdr() {
        return zdr;
    }

    public void setZdr(String zdr) {
        this.zdr = zdr;
    }

    public Double getSdjy() {
        return sdjy;
    }

    public void setSdjy(Double sdjy) {
        this.sdjy = sdjy;
    }

    public Double getBcyshk() {
        return bcyshk;
    }

    public void setBcyshk(Double bcyshk) {
        this.bcyshk = bcyshk;
    }

    public Double getDfyf() {
        return dfyf;
    }

    public void setDfyf(Double dfyf) {
        this.dfyf = dfyf;
    }

    public Double getBcsk() {
        return bcsk;
    }

    public void setBcsk(Double bcsk) {
        this.bcsk = bcsk;
    }

    public Double getYhje() {
        return yhje;
    }

    public void setYhje(Double yhje) {
        this.yhje = yhje;
    }

    public Double getDj() {
        return dj;
    }

    public void setDj(Double dj) {
        this.dj = dj;
    }

    public Double getBzj() {
        return bzj;
    }

    public void setBzj(Double bzj) {
        this.bzj = bzj;
    }

    public Double getLjye() {
        return ljye;
    }

    public void setLjye(Double ljye) {
        this.ljye = ljye;
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

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
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
