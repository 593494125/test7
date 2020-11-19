package com.springboot.service.org;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.org.BaseOrg;

import java.util.List;
import java.util.Map;

public interface BaseOrgService extends IService<BaseOrg> {


    List<BaseOrg> getList(String orgId, Map<String, String> param);

    void insertBean(String orgId, BaseOrg bean);

    void deleteById(String orgId, String id);

    Integer updateById(String orgId, BaseOrg BaseOrg);

    BaseOrg findById(String sixCode, String id);

}
