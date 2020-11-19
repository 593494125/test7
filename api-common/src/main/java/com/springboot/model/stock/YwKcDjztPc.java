package com.springboot.model.stock;

import com.springboot.model.BasePageForm;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-05-18
 */
public class YwKcDjztPc extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pzh;
    private String dqzt;
    private String czr;
    private Date czsj;
    private String bdcz;
    private String bzxx;
    private String sgdj;


    public String getPzh() {
        return pzh;
    }

    public void setPzh(String pzh) {
        this.pzh = pzh;
    }

    public String getDqzt() {
        return dqzt;
    }

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getBdcz() {
        return bdcz;
    }

    public void setBdcz(String bdcz) {
        this.bdcz = bdcz;
    }

    public String getBzxx() {
        return bzxx;
    }

    public void setBzxx(String bzxx) {
        this.bzxx = bzxx;
    }

    public String getSgdj() {
        return sgdj;
    }

    public void setSgdj(String sgdj) {
        this.sgdj = sgdj;
    }

    @Override
    public String toString() {
        return "YwKcDjztPc{" +
        ", pzh=" + pzh +
        ", dqzt=" + dqzt +
        ", czr=" + czr +
        ", czsj=" + czsj +
        ", bdcz=" + bdcz +
        ", bzxx=" + bzxx +
        ", sgdj=" + sgdj +
        "}";
    }
}
