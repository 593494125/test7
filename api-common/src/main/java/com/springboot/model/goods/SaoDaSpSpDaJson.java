package com.springboot.model.goods;

import java.io.Serializable;

public class SaoDaSpSpDaJson implements Serializable,Cloneable {

    private String spkh;//商品款号
    private String spbh;//商品编号
    private String bmbh;//部门编号
    private String bmmc;//部门名称
    private String ysbh;//颜色编号
    private String ysmc;//颜色名称
    private String bxbh;//版型编号
    private String bxmc;//版型名称
    private String cmbh;//尺码编号
    private String cmbt;//尺码标题
    private String kcsl;//库存
    private String xssl;//销售
    private String cmzbm;//
    private String cm;//


    public String getSpkh() {
        return spkh;
    }

    public void setSpkh(String spkh) {
        this.spkh = spkh;
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

    public String getYsmc() {
        return ysmc;
    }

    public void setYsmc(String ysmc) {
        this.ysmc = ysmc;
    }

    public String getBxbh() {
        return bxbh;
    }

    public void setBxbh(String bxbh) {
        this.bxbh = bxbh;
    }

    public String getBxmc() {
        return bxmc;
    }

    public void setBxmc(String bxmc) {
        this.bxmc = bxmc;
    }

    public String getCmbt() {
        return cmbt;
    }

    public void setCmbt(String cmbt) {
        this.cmbt = cmbt;
    }

    public String getKcsl() {
        return kcsl;
    }

    public void setKcsl(String kcsl) {
        this.kcsl = kcsl;
    }

    public String getXssl() {
        return xssl;
    }

    public void setXssl(String xssl) {
        this.xssl = xssl;
    }

    public String getCmzbm() {
        return cmzbm;
    }

    public void setCmzbm(String cmzbm) {
        this.cmzbm = cmzbm;
    }

    public String getCm() {
        return cm;
    }

    public String getCmbh() {
        return cmbh;
    }

    public void setCmbh(String cmbh) {
        this.cmbh = cmbh;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    @Override
    public Object clone(){
        try {
            return (SaoDaSpSpDaJson)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public SaoDaSpSpDaJson() {
    }
}
