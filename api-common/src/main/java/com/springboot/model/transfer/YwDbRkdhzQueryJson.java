package com.springboot.model.transfer;

import com.springboot.model.BasePageForm;

public class YwDbRkdhzQueryJson extends BasePageForm {

    private String startTime;
    private String endTime;
    private String bmbh1;//调出
    private String bmbh2;//调入
    private String cgy;
    private String pzh;
    private String djlx;//1：调出2：调入

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBmbh1() {
        return bmbh1;
    }

    public void setBmbh1(String bmbh1) {
        this.bmbh1 = bmbh1;
    }

    public String getBmbh2() {
        return bmbh2;
    }

    public void setBmbh2(String bmbh2) {
        this.bmbh2 = bmbh2;
    }

    public String getCgy() {
        return cgy;
    }

    public void setCgy(String cgy) {
        this.cgy = cgy;
    }

    public String getPzh() {
        return pzh;
    }

    public void setPzh(String pzh) {
        this.pzh = pzh;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public YwDbRkdhzQueryJson() {
    }
}
