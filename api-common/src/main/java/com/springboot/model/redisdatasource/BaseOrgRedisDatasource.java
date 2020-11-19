package com.springboot.model.redisdatasource;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjq
 * @since 2020-07-23
 */
public class BaseOrgRedisDatasource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String orgId;
    private String redisId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRedisId() {
        return redisId;
    }

    public void setRedisId(String redisId) {
        this.redisId = redisId;
    }

    @Override
    public String toString() {
        return "BaseOrgRedisDatasource{" +
        ", id=" + id +
        ", orgId=" + orgId +
        ", redisId=" + redisId +
        "}";
    }
}
