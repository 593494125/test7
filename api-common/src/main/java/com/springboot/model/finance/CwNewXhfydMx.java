package com.springboot.model.finance;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * <p>
 * 销货费用单明细
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public class CwNewXhfydMx implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double je;
    private String mxdh;
    private String sm;
    private String sfkm;
    private String pzh;

    @TableField(exist = false)
    private String kmmc;//科目名称


    public Double getJe() {
        return je;
    }

    public void setJe(Double je) {
        this.je = je;
    }

    public String getMxdh() {
        return mxdh;
    }

    public void setMxdh(String mxdh) {
        this.mxdh = mxdh;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getSfkm() {
        return sfkm;
    }

    public void setSfkm(String sfkm) {
        this.sfkm = sfkm;
    }

    public String getPzh() {
        return pzh;
    }

    public void setPzh(String pzh) {
        this.pzh = pzh;
    }

    public String getKmmc() {
        return kmmc;
    }

    public void setKmmc(String kmmc) {
        this.kmmc = kmmc;
    }

    @Override
    public String toString() {
        return "CwNewXhfydMx{" +
        ", je=" + je +
        ", mxdh=" + mxdh +
        ", sm=" + sm +
        ", sfkm=" + sfkm +
        ", pzh=" + pzh +
        "}";
    }
}
