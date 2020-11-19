package com.springboot.dao.redisdatasource;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.redisdatasource.BaseRedisDatasource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseRedisDatasourceMapper extends BaseMapper<BaseRedisDatasource> {

    List<BaseRedisDatasource> getList(Map<String, String> param);

    List<String> getRedisSelectList(@Param("areaAddress") String areaAddress);

    List<String> getAreaSelectList();

}
