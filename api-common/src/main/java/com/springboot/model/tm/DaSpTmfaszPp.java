package com.springboot.model.tm;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-06-17
 */
public class DaSpTmfaszPp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ppbh;
    private String jqfa;
    private String zjws;
    private String zjqsws;
    private String zjjsws;
    private String ydqws;
    private String sfqy;
    private String mrtmfa;
    private String bz;


    public String getPpbh() {
        return ppbh;
    }

    public void setPpbh(String ppbh) {
        this.ppbh = ppbh;
    }

    public String getJqfa() {
        return jqfa;
    }

    public void setJqfa(String jqfa) {
        this.jqfa = jqfa;
    }

    public String getZjws() {
        return zjws;
    }

    public void setZjws(String zjws) {
        this.zjws = zjws;
    }

    public String getZjqsws() {
        return zjqsws;
    }

    public void setZjqsws(String zjqsws) {
        this.zjqsws = zjqsws;
    }

    public String getZjjsws() {
        return zjjsws;
    }

    public void setZjjsws(String zjjsws) {
        this.zjjsws = zjjsws;
    }

    public String getYdqws() {
        return ydqws;
    }

    public void setYdqws(String ydqws) {
        this.ydqws = ydqws;
    }

    public String getSfqy() {
        return sfqy;
    }

    public void setSfqy(String sfqy) {
        this.sfqy = sfqy;
    }

    public String getMrtmfa() {
        return mrtmfa;
    }

    public void setMrtmfa(String mrtmfa) {
        this.mrtmfa = mrtmfa;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public DaSpTmfaszPp() {
    }

    @Override
    public String toString() {
        return "DaSpTmfaszPp{" +
        ", ppbh=" + ppbh +
        ", jqfa=" + jqfa +
        ", zjws=" + zjws +
        ", zjqsws=" + zjqsws +
        ", zjjsws=" + zjjsws +
        ", ydqws=" + ydqws +
        ", sfqy=" + sfqy +
        ", mrtmfa=" + mrtmfa +
        ", bz=" + bz +
        "}";
    }
}
