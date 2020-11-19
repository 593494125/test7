package com.springboot.service.impl.redisdatasource;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.redisdatasource.BaseOrgRedisDatasourceMapper;
import com.springboot.model.redisdatasource.BaseOrgRedisDatasource;
import com.springboot.service.redisdatasource.BaseOrgRedisDatasourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseOrgRedisDatasourceServiceImpl extends ServiceImpl<BaseOrgRedisDatasourceMapper, BaseOrgRedisDatasource> implements BaseOrgRedisDatasourceService {


    @Resource
    private BaseOrgRedisDatasourceMapper baseOrgRedisDatasourceMapper;
    @Override
    public void insertBean(String sixCode, BaseOrgRedisDatasource bean) {
        baseOrgRedisDatasourceMapper.insert(bean);
    }

    @Override
    public BaseOrgRedisDatasource findByOrgId(String sixCode, String orgId) {
        BaseOrgRedisDatasource querybean=new BaseOrgRedisDatasource();
        querybean.setOrgId(orgId);
        BaseOrgRedisDatasource bean= baseOrgRedisDatasourceMapper.selectOne(querybean);
        return bean;
    }

    @Override
    public Integer getCountByRedisId(String sixCode, String redisId) {
        Integer count =baseOrgRedisDatasourceMapper.getCountByRedisId(redisId);
        return count;
    }
}
