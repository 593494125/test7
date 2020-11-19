package com.springboot.model.system;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-07-06
 */
public class DaQjCsb implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer lsh;
    private String csbh;
    private String csmc;
    private String csfl;
    private String qybz;
    private String bzxx;


    public Integer getLsh() {
        return lsh;
    }

    public void setLsh(Integer lsh) {
        this.lsh = lsh;
    }

    public String getCsbh() {
        return csbh;
    }

    public void setCsbh(String csbh) {
        this.csbh = csbh;
    }

    public String getCsmc() {
        return csmc;
    }

    public void setCsmc(String csmc) {
        this.csmc = csmc;
    }

    public String getCsfl() {
        return csfl;
    }

    public void setCsfl(String csfl) {
        this.csfl = csfl;
    }

    public String getQybz() {
        return qybz;
    }

    public void setQybz(String qybz) {
        this.qybz = qybz;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    @Override
    public String toString() {
        return "DaQjCsb{" +
        ", lsh=" + lsh +
        ", csbh=" + csbh +
        ", csmc=" + csmc +
        ", csfl=" + csfl +
        ", qybz=" + qybz +
        ", bzxx=" + bzxx +
        "}";
    }
}
