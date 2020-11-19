package com.springboot.service.org;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.datasource.BaseDatasource;
import com.springboot.model.org.BaseOrg;

import java.util.List;
import java.util.Map;

public interface BaseOrgService extends IService<BaseOrg> {

    Page<BaseOrg> getPage(String orgId,Page<BaseOrg> page,BaseOrg org,Map<String, String> param);

    List<BaseOrg> getList(String orgId,Map<String, String> param);

    void insertBean(String orgId,BaseOrg bean);

    void deleteById(String orgId,String id);

    Integer updateById(String orgId, BaseOrg BaseOrg);

    BaseOrg findById(String sixCode,String id);

    String findIsStartTmFangan(String sixCode,String orgId);

    List<String> getSelectList(String sixCode,String areaAddress);

}
