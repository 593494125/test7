package com.springboot.model.finance;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-09-12
 */
public class YwPosMxSpkh implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pzh;
    private String lydh;
    private Double pfjg;
    private Double lsj;
    private Date rq;
    private String flag;
    private String spkh;
    private Double cbj;
    private Double danjia;
    private String qjfs;
    private Double pfze;
    private Integer sl;
    private Double dbjg;
    private Double zjje;
    private String bmbh;
    private Date ywrq;


    public String getPzh() {
        return pzh;
    }

    public void setPzh(String pzh) {
        this.pzh = pzh;
    }

    public String getLydh() {
        return lydh;
    }

    public void setLydh(String lydh) {
        this.lydh = lydh;
    }

    public Double getPfjg() {
        return pfjg;
    }

    public void setPfjg(Double pfjg) {
        this.pfjg = pfjg;
    }

    public Double getLsj() {
        return lsj;
    }

    public void setLsj(Double lsj) {
        this.lsj = lsj;
    }

    public Date getRq() {
        return rq;
    }

    public void setRq(Date rq) {
        this.rq = rq;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSpkh() {
        return spkh;
    }

    public void setSpkh(String spkh) {
        this.spkh = spkh;
    }

    public Double getCbj() {
        return cbj;
    }

    public void setCbj(Double cbj) {
        this.cbj = cbj;
    }

    public Double getDanjia() {
        return danjia;
    }

    public void setDanjia(Double danjia) {
        this.danjia = danjia;
    }

    public String getQjfs() {
        return qjfs;
    }

    public void setQjfs(String qjfs) {
        this.qjfs = qjfs;
    }

    public Double getPfze() {
        return pfze;
    }

    public void setPfze(Double pfze) {
        this.pfze = pfze;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public Double getDbjg() {
        return dbjg;
    }

    public void setDbjg(Double dbjg) {
        this.dbjg = dbjg;
    }

    public Double getZjje() {
        return zjje;
    }

    public void setZjje(Double zjje) {
        this.zjje = zjje;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public Date getYwrq() {
        return ywrq;
    }

    public void setYwrq(Date ywrq) {
        this.ywrq = ywrq;
    }

    @Override
    public String toString() {
        return "YwPosMxSpkh{" +
        ", pzh=" + pzh +
        ", lydh=" + lydh +
        ", pfjg=" + pfjg +
        ", lsj=" + lsj +
        ", rq=" + rq +
        ", flag=" + flag +
        ", spkh=" + spkh +
        ", cbj=" + cbj +
        ", danjia=" + danjia +
        ", qjfs=" + qjfs +
        ", pfze=" + pfze +
        ", sl=" + sl +
        ", dbjg=" + dbjg +
        ", zjje=" + zjje +
        ", bmbh=" + bmbh +
        ", ywrq=" + ywrq +
        "}";
    }
}
