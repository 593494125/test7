package com.springboot.dao.datasource;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.datasource.BaseDatasource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2019/10/18.
 */
public interface BaseDatasourceMapper extends BaseMapper<BaseDatasource> {

    List<BaseDatasource> getList(Map<String, String> param);

    Integer getCountByOrgId(@Param("orgId") String orgId);

    BaseDatasource getBeanByOrgId(@Param("orgId") String orgId);

    void deleteByOrgId(@Param("orgId") String orgId);

}
