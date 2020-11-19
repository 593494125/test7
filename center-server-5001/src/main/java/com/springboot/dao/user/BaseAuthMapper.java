package com.springboot.dao.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.user.BaseAuth;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-06-10
 */
public interface BaseAuthMapper extends BaseMapper<BaseAuth> {

    Integer getCountByOrgId(@Param("orgId") String orgId);

    Integer deleteByOrgId(@Param("orgId") String orgId);

    Integer updateA8Bean(@Param("sixCode") String sixCode);

}
