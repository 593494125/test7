package com.springboot.model.user;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.BasePageForm;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-06-10
 */
public class BaseAuth extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Integer state;
    private String sixCode;
    private String orgId;
    private String effectStartTime;
    private String effectEndTime;

    @TableField(exist=false)
    private String orgName;
    @TableField(exist=false)
    private String count;//注册人数


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSixCode() {
        return sixCode;
    }

    public void setSixCode(String sixCode) {
        this.sixCode = sixCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getEffectStartTime() {
        return effectStartTime;
    }

    public void setEffectStartTime(String effectStartTime) {
        this.effectStartTime = effectStartTime;
    }

    public String getEffectEndTime() {
        return effectEndTime;
    }

    public void setEffectEndTime(String effectEndTime) {
        this.effectEndTime = effectEndTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BaseAuth{" +
        ", id=" + id +
        ", title=" + title +
        ", state=" + state +
        ", sixCode=" + sixCode +
        ", orgId=" + orgId +
        ", effectStartTime=" + effectStartTime +
        ", effectEndTime=" + effectEndTime +
        "}";
    }
}
