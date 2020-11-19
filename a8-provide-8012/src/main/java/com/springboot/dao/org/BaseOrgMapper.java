package com.springboot.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.org.BaseOrg;

import java.util.List;
import java.util.Map;

public interface BaseOrgMapper extends BaseMapper<BaseOrg> {

     List<BaseOrg> getList(Map<String, String> param);
}
