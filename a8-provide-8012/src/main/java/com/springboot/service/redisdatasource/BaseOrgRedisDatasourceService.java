package com.springboot.service.redisdatasource;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.redisdatasource.BaseOrgRedisDatasource;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-07-23
 */
public interface BaseOrgRedisDatasourceService extends IService<BaseOrgRedisDatasource> {

    void insertBean(String sixCode, BaseOrgRedisDatasource bean);

    BaseOrgRedisDatasource findByOrgId(String sixCode, String orgId);

    Integer getCountByRedisId(String sixCode, String redisId);

}
