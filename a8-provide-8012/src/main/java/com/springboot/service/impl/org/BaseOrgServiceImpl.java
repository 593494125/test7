package com.springboot.service.impl.org;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.RedisUtil;
import com.springboot.dao.org.BaseOrgMapper;
import com.springboot.model.org.BaseOrg;
import com.springboot.service.org.BaseOrgService;
import com.springboot.service.user.BaseAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class BaseOrgServiceImpl extends ServiceImpl<BaseOrgMapper, BaseOrg> implements BaseOrgService {

    @Resource
    private BaseOrgMapper baseOrgMapper;
    @Resource
    private BaseAuthService baseAuthService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<BaseOrg> getList(String orgId, Map<String, String> param) {
        List<BaseOrg> list=baseOrgMapper.getList(param);
        return list;
    }

    @Override
    public void insertBean(String orgId,BaseOrg bean) {
        baseOrgMapper.insert(bean);
    }

    @Override
    public void deleteById(String orgId, String id) {
        baseOrgMapper.deleteById(id);
    }

    @Override
    public Integer updateById(String orgId, BaseOrg BaseOrg) {
        Integer count=baseOrgMapper.updateById(BaseOrg);
        return count;
    }

    @Override
    public BaseOrg findById(String sixCode, String id) {
        BaseOrg org=baseOrgMapper.selectById(id);
        return org;
    }


}
