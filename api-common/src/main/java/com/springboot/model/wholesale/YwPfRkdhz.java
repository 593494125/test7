package com.springboot.model.wholesale;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.BasePageForm;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public class YwPfRkdhz extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pzh;
    private Date kdrq;
    private String bmbh;
    private String khbh;
    private String khdh;
    private Double zjsl;
    private Double zjje;
    private String cgy;
    private String kdr;
    private String cgfs;
    private String xxly;
    private String lydh;
    private String djlx;
    private String jzbz;
    private String bzxx;
    private String fhlx;
    private String fklx;
//    private Date sjc;
    private String xgr;
    private Date xgsj;
    private String jzr;
    private Date jzsj;
    private Double sdjy;
    private String skfs;
    private Double bdsk;
    private Double dfyf;
    private Double bdyh;
    private Double ljqk;
    private String zh;
    private Double khsdjy;
    private Double khljqk;
    private Double newsdjy;
    private Double newbdsk;
    private Double newdfyf;
    private Double newbdyh;
    private Double newljqk;
    private Double newkhsdjy;
    private Double newkhljqk;
    private Date kdsj;
    private Double lsjze;
    private String jsr;
    private String jzbzCg;

    @TableField(exist = false)
    private String newkdrq;//开单日期
    @TableField(exist = false)
    private String newkdsj;//开单时间
    @TableField(exist = false)
    private String cgymc;//采购人名称
    @TableField(exist = false)
    private String bmmc;//部门名称
    @TableField(exist = false)
    private String khmc;//客户名称
    @TableField(exist = false)
    private String sjhm;//客户手机号码
    @TableField(exist = false)
    private Double bcyshk;//本次应收货款
    @TableField(exist = false)
    private String ygmc;//业务员
    @TableField(exist = false)
    private String[] tmbhAndsl;//条码编号和数量

    public String getPzh() {
        return pzh;
    }

    public void setPzh(String pzh) {
        this.pzh = pzh;
    }

    public Date getKdrq() {
        return kdrq;
    }

    public void setKdrq(Date kdrq) {
        this.kdrq = kdrq;
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

    public String getKhdh() {
        return khdh;
    }

    public void setKhdh(String khdh) {
        this.khdh = khdh;
    }

    public Double getZjsl() {
        return zjsl;
    }

    public void setZjsl(Double zjsl) {
        this.zjsl = zjsl;
    }

    public Double getZjje() {
        return zjje;
    }

    public void setZjje(Double zjje) {
        this.zjje = zjje;
    }

    public String getCgy() {
        return cgy;
    }

    public void setCgy(String cgy) {
        this.cgy = cgy;
    }

    public String getKdr() {
        return kdr;
    }

    public void setKdr(String kdr) {
        this.kdr = kdr;
    }

    public String getCgfs() {
        return cgfs;
    }

    public void setCgfs(String cgfs) {
        this.cgfs = cgfs;
    }

    public String getXxly() {
        return xxly;
    }

    public void setXxly(String xxly) {
        this.xxly = xxly;
    }

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getJzbz() {
        return jzbz;
    }

    public void setJzbz(String jzbz) {
        this.jzbz = jzbz;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    public String getFhlx() {
        return fhlx;
    }

    public void setFhlx(String fhlx) {
        this.fhlx = fhlx;
    }

    public String getFklx() {
        return fklx;
    }

    public void setFklx(String fklx) {
        this.fklx = fklx;
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

    public Double getSdjy() {
        return sdjy;
    }

    public void setSdjy(Double sdjy) {
        this.sdjy = sdjy;
    }

    public String getSkfs() {
        return skfs;
    }

    public void setSkfs(String skfs) {
        this.skfs = skfs;
    }

    public Double getBdsk() {
        return bdsk;
    }

    public void setBdsk(Double bdsk) {
        this.bdsk = bdsk;
    }

    public Double getDfyf() {
        return dfyf;
    }

    public void setDfyf(Double dfyf) {
        this.dfyf = dfyf;
    }

    public Double getBdyh() {
        return bdyh;
    }

    public void setBdyh(Double bdyh) {
        this.bdyh = bdyh;
    }

    public Double getLjqk() {
        return ljqk;
    }

    public void setLjqk(Double ljqk) {
        this.ljqk = ljqk;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public Double getKhsdjy() {
        return khsdjy;
    }

    public void setKhsdjy(Double khsdjy) {
        this.khsdjy = khsdjy;
    }

    public Double getKhljqk() {
        return khljqk;
    }

    public void setKhljqk(Double khljqk) {
        this.khljqk = khljqk;
    }

    public Double getNewsdjy() {
        return newsdjy;
    }

    public void setNewsdjy(Double newsdjy) {
        this.newsdjy = newsdjy;
    }

    public Double getNewbdsk() {
        return newbdsk;
    }

    public void setNewbdsk(Double newbdsk) {
        this.newbdsk = newbdsk;
    }

    public Double getNewdfyf() {
        return newdfyf;
    }

    public void setNewdfyf(Double newdfyf) {
        this.newdfyf = newdfyf;
    }

    public Double getNewbdyh() {
        return newbdyh;
    }

    public void setNewbdyh(Double newbdyh) {
        this.newbdyh = newbdyh;
    }

    public Double getNewljqk() {
        return newljqk;
    }

    public void setNewljqk(Double newljqk) {
        this.newljqk = newljqk;
    }

    public Double getNewkhsdjy() {
        return newkhsdjy;
    }

    public void setNewkhsdjy(Double newkhsdjy) {
        this.newkhsdjy = newkhsdjy;
    }

    public Double getNewkhljqk() {
        return newkhljqk;
    }

    public void setNewkhljqk(Double newkhljqk) {
        this.newkhljqk = newkhljqk;
    }

    public Date getKdsj() {
        return kdsj;
    }

    public void setKdsj(Date kdsj) {
        this.kdsj = kdsj;
    }

    public Double getLsjze() {
        return lsjze;
    }

    public void setLsjze(Double lsjze) {
        this.lsjze = lsjze;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    public String getJzbzCg() {
        return jzbzCg;
    }

    public void setJzbzCg(String jzbzCg) {
        this.jzbzCg = jzbzCg;
    }

    public String getNewkdrq() {
        return newkdrq;
    }

    public void setNewkdrq(String newkdrq) {
        this.newkdrq = newkdrq;
    }

    public String getNewkdsj() {
        return newkdsj;
    }

    public void setNewkdsj(String newkdsj) {
        this.newkdsj = newkdsj;
    }

    public String getCgymc() {
        return cgymc;
    }

    public void setCgymc(String cgymc) {
        this.cgymc = cgymc;
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

    public String getYgmc() {
        return ygmc;
    }

    public void setYgmc(String ygmc) {
        this.ygmc = ygmc;
    }

    public String[] getTmbhAndsl() {
        return tmbhAndsl;
    }

    public void setTmbhAndsl(String[] tmbhAndsl) {
        this.tmbhAndsl = tmbhAndsl;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public Double getBcyshk() {
        return bcyshk;
    }

    public void setBcyshk(Double bcyshk) {
        this.bcyshk = bcyshk;
    }
}
