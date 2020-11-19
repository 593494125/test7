package com.springboot.service.user;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.user.BaseAuth;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-06-10
 */
public interface BaseAuthService extends IService<BaseAuth> {

    Page<BaseAuth> getPage(String sixCode, Page<BaseAuth> page, Map<String, String> param);

    void insertBean(String sixCode, BaseAuth bean);

    Integer deleteById(String sixCode, String id);

    Integer deleteByOrgId(String sixCode, String orgId);

    Integer updateById(String sixCode, BaseAuth bean);

    Integer updateA8Bean(String sixCode);

    BaseAuth findById(String sixCode, String id);

    BaseAuth findByorgId(String sixCode, String orgId);

    Integer getCountByOrgId(String sixCode, String orgId);
}
