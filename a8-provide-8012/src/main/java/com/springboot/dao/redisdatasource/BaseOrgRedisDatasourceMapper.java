package com.springboot.dao.redisdatasource;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.redisdatasource.BaseOrgRedisDatasource;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-07-23
 */
public interface BaseOrgRedisDatasourceMapper extends BaseMapper<BaseOrgRedisDatasource> {

    Integer getCountByRedisId(@Param("redisId") String redisId);

}
