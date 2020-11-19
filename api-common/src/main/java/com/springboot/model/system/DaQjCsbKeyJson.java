package com.springboot.model.system;

import com.springboot.model.purchase.YwCgRkdmxJson;

import java.io.Serializable;

public class DaQjCsbKeyJson implements Serializable ,Cloneable{

    private String csmc;
    private String qybz;
    private String bzxx;

    public String getCsmc() {
        return csmc;
    }

    public void setCsmc(String csmc) {
        this.csmc = csmc;
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
    public Object clone(){
        try {
            return (DaQjCsbKeyJson)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
