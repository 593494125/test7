package com.springboot.service.redisdatasource;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.redisdatasource.BaseRedisDatasource;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-07-22
 */
public interface BaseRedisDatasourceService extends IService<BaseRedisDatasource> {


    List<BaseRedisDatasource> getList(String sixCode, Map<String, String> param);

    void insertBean(String sixCode, BaseRedisDatasource bean);

    void deleteById(String sixCode, BaseRedisDatasource bean);

    void deleteByOrgId(String sixCode, String orgId);

    Integer updateById(String sixCode, BaseRedisDatasource bean);

    BaseRedisDatasource findById(String sixCode, String id);

    BaseRedisDatasource findOne(String sixCode, Map<String, String> param);

    List<String> getRedisSelectList(String sixCode, String areaAddress);

    List<String> getAreaSelectList(String sixCode);

}
