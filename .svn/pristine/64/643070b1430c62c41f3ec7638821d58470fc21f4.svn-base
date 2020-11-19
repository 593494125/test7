package com.springboot.model;

import com.baomidou.mybatisplus.annotations.TableField;

public class BasePageForm {

    @TableField(exist=false)
    private String yhbh;
    @TableField(exist=false)
    private String sixCode;
    @TableField(exist=false)
    private Integer pageNo = 1; //前端页面默认都从1开始
    @TableField(exist=false)
    private Integer pageSize = 20;

    public Integer getPageNo() {
        if (pageNo > 0) {
            return pageNo;
        } else {
            return 0;
        }
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSixCode() {
        return sixCode;
    }

    public void setSixCode(String sixCode) {
        this.sixCode = sixCode;
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

}
