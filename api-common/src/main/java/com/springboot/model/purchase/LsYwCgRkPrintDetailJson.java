package com.springboot.model.purchase;

import java.io.Serializable;

public class LsYwCgRkPrintDetailJson implements Serializable,Cloneable{

//    private String ysbxcm;
    private String sl;
    private String lsj;
    private String dpj;
    private String zkl;
    private String jsj;
    private String je;
    private String danjia;

    private String spbh;
    private String ysbh;
    private String ysmc;
    private String yslsh;
    private String bxbh;
    private String bxmc;
    private String cmbh;
    private String bqkc;
    private String tmbh;
    private String cmzbm;
    private String cmzlwz;
    private String cmbt;
    private String yyyh;
    private String zke;


    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getLsj() {
        return lsj;
    }

    public void setLsj(String lsj) {
        this.lsj = lsj;
    }

    public String getZkl() {
        return zkl;
    }

    public void setZkl(String zkl) {
        this.zkl = zkl;
    }

    public String getJsj() {
        return jsj;
    }

    public void setJsj(String jsj) {
        this.jsj = jsj;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getDanjia() {
        return danjia;
    }

    public void setDanjia(String danjia) {
        this.danjia = danjia;
    }

    public String getSpbh() {
        return spbh;
    }

    public void setSpbh(String spbh) {
        this.spbh = spbh;
    }

    public String getYsbh() {
        return ysbh;
    }

    public void setYsbh(String ysbh) {
        this.ysbh = ysbh;
    }

    public String getYslsh() {
        return yslsh;
    }

    public void setYslsh(String yslsh) {
        this.yslsh = yslsh;
    }

    public String getBxbh() {
        return bxbh;
    }

    public void setBxbh(String bxbh) {
        this.bxbh = bxbh;
    }

    public String getCmbh() {
        return cmbh;
    }

    public void setCmbh(String cmbh) {
        this.cmbh = cmbh;
    }

    public String getBqkc() {
        return bqkc;
    }

    public void setBqkc(String bqkc) {
        this.bqkc = bqkc;
    }

    public String getTmbh() {
        return tmbh;
    }

    public void setTmbh(String tmbh) {
        this.tmbh = tmbh;
    }

    public String getCmzbm() {
        return cmzbm;
    }

    public void setCmzbm(String cmzbm) {
        this.cmzbm = cmzbm;
    }

    public String getBxmc() {
        return bxmc;
    }

    public void setBxmc(String bxmc) {
        this.bxmc = bxmc;
    }

    public String getYsmc() {
        return ysmc;
    }

    public void setYsmc(String ysmc) {
        this.ysmc = ysmc;
    }

    public String getCmbt() {
        return cmbt;
    }

    public void setCmbt(String cmbt) {
        this.cmbt = cmbt;
    }

    public String getYyyh() {
        return yyyh;
    }

    public void setYyyh(String yyyh) {
        this.yyyh = yyyh;
    }

    public String getZke() {
        return zke;
    }

    public void setZke(String zke) {
        this.zke = zke;
    }

    public String getDpj() {
        return dpj;
    }

    public void setDpj(String dpj) {
        this.dpj = dpj;
    }

    public String getCmzlwz() {
        return cmzlwz;
    }

    public void setCmzlwz(String cmzlwz) {
        this.cmzlwz = cmzlwz;
    }

    @Override
    public Object clone(){
        try {
            return (LsYwCgRkPrintDetailJson)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
