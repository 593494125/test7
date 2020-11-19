package com.springboot.service.datasource;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.datasource.BaseDatasource;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2019/10/18.
 */
public interface BaseDatasourceService extends IService<BaseDatasource> {

    Page<BaseDatasource> getPage(String orgId, Page<BaseDatasource> page, Map<String, String> param);

    void insertBean(String orgId, BaseDatasource bean);

    List<BaseDatasource> getList(String orgId, Map<String, String> param);

    void deleteById(String orgId, String id);

    void deleteByOrgId(String sixCode, String orgId);

    Integer updateById(String orgId, BaseDatasource baseDatasource);

    Integer getCountByOrgId(String sixCode, String orgId);

    BaseDatasource getBeanByOrgId(String sixCode, String orgId);
}
