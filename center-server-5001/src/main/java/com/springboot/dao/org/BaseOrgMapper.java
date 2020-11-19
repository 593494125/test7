package com.springboot.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.org.BaseOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseOrgMapper extends BaseMapper<BaseOrg> {

     List<BaseOrg> getList(Map<String, String> param);

     List<BaseOrg> getPage(@Param("key") String key,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

     String findIsStartTmFangan(@Param("orgId") String orgId);

     List<String> getSelectList(@Param("areaAddress") String areaAddress);

     List<String> getAreaSelectList();

     List<String> getHsotList();
}
