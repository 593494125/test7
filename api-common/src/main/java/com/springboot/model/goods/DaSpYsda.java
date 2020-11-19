package com.springboot.model.goods;

import java.io.Serializable;

/**
 * <p>
 * 商品颜色档案
 * </p>
 *
 * @author zjq
 * @since 2020-04-30
 */
public class DaSpYsda implements Serializable {

    private static final long serialVersionUID = 1L;

    private String yslsh;
    private String ysbh;
    private String ysmc;
    private String sxbh;
    private String qyfs;
    private String jp;


    public String getYslsh() {
        return yslsh;
    }

    public void setYslsh(String yslsh) {
        this.yslsh = yslsh;
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

    public String getSxbh() {
        return sxbh;
    }

    public void setSxbh(String sxbh) {
        this.sxbh = sxbh;
    }

    public String getQyfs() {
        return qyfs;
    }

    public void setQyfs(String qyfs) {
        this.qyfs = qyfs;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    @Override
    public String toString() {
        return "DaSpYsda{" +
        ", yslsh=" + yslsh +
        ", ysbh=" + ysbh +
        ", ysmc=" + ysmc +
        ", sxbh=" + sxbh +
        ", qyfs=" + qyfs +
        ", jp=" + jp +
        "}";
    }
}
