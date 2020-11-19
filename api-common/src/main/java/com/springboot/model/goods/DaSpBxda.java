package com.springboot.model.goods;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-04-30
 */
public class DaSpBxda implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bxbh;
    private String bxmc;
    private String sslb;


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

    public String getSslb() {
        return sslb;
    }

    public void setSslb(String sslb) {
        this.sslb = sslb;
    }

    @Override
    public String toString() {
        return "DaSpBxda{" +
        ", bxbh=" + bxbh +
        ", bxmc=" + bxmc +
        ", sslb=" + sslb +
        "}";
    }
}
