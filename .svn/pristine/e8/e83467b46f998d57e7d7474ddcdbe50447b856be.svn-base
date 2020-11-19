package com.springboot.model.bluetooth;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.BasePageForm;
import com.springboot.model.purchase.YwCgRkListJson;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-08-26
 */
public class BlueToothExt extends BasePageForm implements Serializable,Cloneable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String ppid;
    private String mac;
    private String cgy;
    private Integer sl;
    private Integer qybz;
    private String userId;
    private String qyTime;
    private String deviceName;
    private String createTime;
    private String updateTime;
    private String sn;

    @TableField(exist = false)
    private String[] macArr;
    @TableField(exist = false)
    private String type;
    @TableField(exist = false)
    private String key;//模糊查询使用


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String[] getMacArr() {
        return macArr;
    }

    public void setMacArr(String[] macArr) {
        this.macArr = macArr;
    }

    public String getCgy() {
        return cgy;
    }

    public void setCgy(String cgy) {
        this.cgy = cgy;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public Integer getQybz() {
        return qybz;
    }

    public void setQybz(Integer qybz) {
        this.qybz = qybz;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQyTime() {
        return qyTime;
    }

    public void setQyTime(String qyTime) {
        this.qyTime = qyTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Object clone(){
        try {
            return (BlueToothExt)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
