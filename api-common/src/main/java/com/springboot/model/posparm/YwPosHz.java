package com.springboot.model.posparm;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * POS汇总表
 * </p>
 *
 * @author zjq
 * @since 2020-07-10
 */
public class YwPosHz implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易号
     */
    private String jyh;
    /**
     * 部门编号
     */
    private String bmbh;
    /**
     * 日期
     */
    private Date rq;
    /**
     * 收款员号
     */
    private String skyh;
    /**
     * 数量
     */
    private Double sl;
    /**
     * 零售总额
     */
    private Double lsje;
    /**
     * 折扣金额
     */
    private Double zkje;
    /**
     * 日结
     */
    private String rj;
    /**
     * 机器码号
     */
    private String jqbh;
    /**
     * 现金
     */
    private Double xj;
    /**
     * 信用卡
     */
    private Double xyk;
    /**
     * 会员卡
     */
    private Double hyk;
    /**
     * 购物券
     */
    private Double gwq;
    /**
     * 内购
     */
    private Double ng;
    /**
     * 内购券
     */
    private Double ngq;
    /**
     * 优惠额
     */
    private Double yhe;
    /**
     * 定金
     */
    private Double dj;
    /**
     * 积分方式
     */
    private String jffs;
    /**
     * 会员号
     */
    private String hyh;
    /**
     * 会员卡号
     */
    private String hykh;
    /**
     * 找零
     */
    private Double zl;
    /**
     * 来源单号
     */
    private String lydh;
    /**
     * 备注
     */
    private String bzxx;
    /**
     * 积分
     */
    private Double jf;
    /**
     * 修改人
     */
    private String xgr;
    /**
     * 修改时间
     */
    private Date xgsj;
    /**
     * 记账人
     */
    private String jzr;
    /**
     * 记账时间
     */
    private Date jzsj;
    /**
     * 记账标记
     */
    private String jzbz;
    /**
     * 购物券号及值
     */
    private String gwqzfc;
    /**
     * 抵用金额
     */
    private Double dyje;
    /**
     * 微信支付标志
     */
    private String wzflag;
    /**
     * 使用积分
     */
    private Double syjf;
    /**
     * 积分金额
     */
    private Double jfje;
    /**
     * 开单时间
     */
    private Date kdsj;
    /**
     * 来源单号类型
     */
    private String lydhlx;
    /**
     * 定金账号类型
     */
    private String djzhlx;
    /**
     * 零售总额
     */
    private Double lsjze;
    private Double zdyzf3;
    private String zfqd;
    private Double zdyzf;
    private Double zdyzf1;
    private String mchtradeno;
    private Double zdyzf4;
    private String paystaus;
    private String payjyh;
    private Double zdyzf5;
    private Double zdyzf2;


    @TableField(exist = false)
    private String bmmc;//部门名称
    @TableField(exist = false)
    private String ygmc;//收款员名称
    @TableField(exist = false)
    private String lxdh;//部门电话
    @TableField(exist = false)
    private String skymc;//收款人名称
    @TableField(exist = false)
    private Double wx;//
    @TableField(exist = false)
    private Double ali;//
    @TableField(exist = false)
    private String hymc;//会员名称
    @TableField(exist = false)
    private Double dqye;//会员卡余额
    @TableField(exist = false)
    private Double dqjf;//当前积分


    public String getJyh() {
        return jyh;
    }

    public void setJyh(String jyh) {
        this.jyh = jyh;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public Date getRq() {
        return rq;
    }

    public void setRq(Date rq) {
        this.rq = rq;
    }

    public String getSkyh() {
        return skyh;
    }

    public void setSkyh(String skyh) {
        this.skyh = skyh;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public Double getLsje() {
        return lsje;
    }

    public void setLsje(Double lsje) {
        this.lsje = lsje;
    }

    public Double getZkje() {
        return zkje;
    }

    public void setZkje(Double zkje) {
        this.zkje = zkje;
    }

    public String getRj() {
        return rj;
    }

    public void setRj(String rj) {
        this.rj = rj;
    }

    public String getJqbh() {
        return jqbh;
    }

    public void setJqbh(String jqbh) {
        this.jqbh = jqbh;
    }

    public Double getXj() {
        return xj;
    }

    public void setXj(Double xj) {
        this.xj = xj;
    }

    public Double getXyk() {
        return xyk;
    }

    public void setXyk(Double xyk) {
        this.xyk = xyk;
    }

    public Double getHyk() {
        return hyk;
    }

    public void setHyk(Double hyk) {
        this.hyk = hyk;
    }

    public Double getGwq() {
        return gwq;
    }

    public void setGwq(Double gwq) {
        this.gwq = gwq;
    }

    public Double getNg() {
        return ng;
    }

    public void setNg(Double ng) {
        this.ng = ng;
    }

    public Double getNgq() {
        return ngq;
    }

    public void setNgq(Double ngq) {
        this.ngq = ngq;
    }

    public Double getYhe() {
        return yhe;
    }

    public void setYhe(Double yhe) {
        this.yhe = yhe;
    }

    public Double getDj() {
        return dj;
    }

    public void setDj(Double dj) {
        this.dj = dj;
    }

    public String getJffs() {
        return jffs;
    }

    public void setJffs(String jffs) {
        this.jffs = jffs;
    }

    public String getHyh() {
        return hyh;
    }

    public void setHyh(String hyh) {
        this.hyh = hyh;
    }

    public String getHykh() {
        return hykh;
    }

    public void setHykh(String hykh) {
        this.hykh = hykh;
    }

    public Double getZl() {
        return zl;
    }

    public void setZl(Double zl) {
        this.zl = zl;
    }

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    public Double getJf() {
        return jf;
    }

    public void setJf(Double jf) {
        this.jf = jf;
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getJzr() {
        return jzr;
    }

    public void setJzr(String jzr) {
        this.jzr = jzr;
    }

    public Date getJzsj() {
        return jzsj;
    }

    public void setJzsj(Date jzsj) {
        this.jzsj = jzsj;
    }

    public String getJzbz() {
        return jzbz;
    }

    public void setJzbz(String jzbz) {
        this.jzbz = jzbz;
    }

    public String getGwqzfc() {
        return gwqzfc;
    }

    public void setGwqzfc(String gwqzfc) {
        this.gwqzfc = gwqzfc;
    }

    public Double getDyje() {
        return dyje;
    }

    public void setDyje(Double dyje) {
        this.dyje = dyje;
    }

    public String getWzflag() {
        return wzflag;
    }

    public void setWzflag(String wzflag) {
        this.wzflag = wzflag;
    }

    public Double getSyjf() {
        return syjf;
    }

    public void setSyjf(Double syjf) {
        this.syjf = syjf;
    }

    public Double getJfje() {
        return jfje;
    }

    public void setJfje(Double jfje) {
        this.jfje = jfje;
    }

    public Date getKdsj() {
        return kdsj;
    }

    public void setKdsj(Date kdsj) {
        this.kdsj = kdsj;
    }

    public String getLydhlx() {
        return lydhlx;
    }

    public void setLydhlx(String lydhlx) {
        this.lydhlx = lydhlx;
    }

    public String getDjzhlx() {
        return djzhlx;
    }

    public void setDjzhlx(String djzhlx) {
        this.djzhlx = djzhlx;
    }

    public Double getLsjze() {
        return lsjze;
    }

    public void setLsjze(Double lsjze) {
        this.lsjze = lsjze;
    }

    public Double getZdyzf3() {
        return zdyzf3;
    }

    public void setZdyzf3(Double zdyzf3) {
        this.zdyzf3 = zdyzf3;
    }

    public String getZfqd() {
        return zfqd;
    }

    public void setZfqd(String zfqd) {
        this.zfqd = zfqd;
    }

    public Double getZdyzf() {
        return zdyzf;
    }

    public void setZdyzf(Double zdyzf) {
        this.zdyzf = zdyzf;
    }

    public Double getZdyzf1() {
        return zdyzf1;
    }

    public void setZdyzf1(Double zdyzf1) {
        this.zdyzf1 = zdyzf1;
    }

    public String getMchtradeno() {
        return mchtradeno;
    }

    public void setMchtradeno(String mchtradeno) {
        this.mchtradeno = mchtradeno;
    }

    public Double getZdyzf4() {
        return zdyzf4;
    }

    public void setZdyzf4(Double zdyzf4) {
        this.zdyzf4 = zdyzf4;
    }

    public String getPaystaus() {
        return paystaus;
    }

    public void setPaystaus(String paystaus) {
        this.paystaus = paystaus;
    }

    public String getPayjyh() {
        return payjyh;
    }

    public void setPayjyh(String payjyh) {
        this.payjyh = payjyh;
    }

    public Double getZdyzf5() {
        return zdyzf5;
    }

    public void setZdyzf5(Double zdyzf5) {
        this.zdyzf5 = zdyzf5;
    }

    public Double getZdyzf2() {
        return zdyzf2;
    }

    public void setZdyzf2(Double zdyzf2) {
        this.zdyzf2 = zdyzf2;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getYgmc() {
        return ygmc;
    }

    public void setYgmc(String ygmc) {
        this.ygmc = ygmc;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getSkymc() {
        return skymc;
    }

    public void setSkymc(String skymc) {
        this.skymc = skymc;
    }

    public Double getWx() {
        return wx;
    }

    public void setWx(Double wx) {
        this.wx = wx;
    }

    public Double getAli() {
        return ali;
    }

    public void setAli(Double ali) {
        this.ali = ali;
    }

    public String getHymc() {
        return hymc;
    }

    public void setHymc(String hymc) {
        this.hymc = hymc;
    }

    public Double getDqye() {
        return dqye;
    }

    public void setDqye(Double dqye) {
        this.dqye = dqye;
    }

    public Double getDqjf() {
        return dqjf;
    }

    public void setDqjf(Double dqjf) {
        this.dqjf = dqjf;
    }

    @Override
    public String toString() {
        return "YwPosHz{" +
        ", jyh=" + jyh +
        ", bmbh=" + bmbh +
        ", rq=" + rq +
        ", skyh=" + skyh +
        ", sl=" + sl +
        ", lsje=" + lsje +
        ", zkje=" + zkje +
        ", rj=" + rj +
        ", jqbh=" + jqbh +
        ", xj=" + xj +
        ", xyk=" + xyk +
        ", hyk=" + hyk +
        ", gwq=" + gwq +
        ", ng=" + ng +
        ", ngq=" + ngq +
        ", yhe=" + yhe +
        ", dj=" + dj +
        ", jffs=" + jffs +
        ", hyh=" + hyh +
        ", hykh=" + hykh +
        ", zl=" + zl +
        ", lydh=" + lydh +
        ", bzxx=" + bzxx +
        ", jf=" + jf +
        ", xgr=" + xgr +
        ", xgsj=" + xgsj +
        ", jzr=" + jzr +
        ", jzsj=" + jzsj +
        ", jzbz=" + jzbz +
        ", gwqzfc=" + gwqzfc +
        ", dyje=" + dyje +
        ", wzflag=" + wzflag +
        ", syjf=" + syjf +
        ", jfje=" + jfje +
        ", kdsj=" + kdsj +
        ", lydhlx=" + lydhlx +
        ", djzhlx=" + djzhlx +
        ", lsjze=" + lsjze +
        ", zdyzf3=" + zdyzf3 +
        ", zfqd=" + zfqd +
        ", zdyzf=" + zdyzf +
        ", zdyzf1=" + zdyzf1 +
        ", mchtradeno=" + mchtradeno +
        ", zdyzf4=" + zdyzf4 +
        ", paystaus=" + paystaus +
        ", payjyh=" + payjyh +
        ", zdyzf5=" + zdyzf5 +
        ", zdyzf2=" + zdyzf2 +
        "}";
    }
}
