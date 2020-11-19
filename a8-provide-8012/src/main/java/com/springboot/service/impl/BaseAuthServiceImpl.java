package com.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.user.a8.BaseAuthMapper;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.user.BaseAuth;
import com.springboot.service.org.BaseOrgService;
import com.springboot.service.user.BaseAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-06-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseAuthServiceImpl extends ServiceImpl<BaseAuthMapper, BaseAuth> implements BaseAuthService {


    @Resource
    private BaseAuthMapper baseAuthMapper;
    @Resource
    private BaseOrgService baseOrgService;
    @Override
    public Page<BaseAuth> getPage(String sixCode, Page<BaseAuth> page, Map<String, String> param) {
        EntityWrapper<BaseAuth> entityWrapper=new EntityWrapper<BaseAuth>();
        if(StringUtils.isNotEmpty(param.get("title"))){
            entityWrapper.eq("title",param.get("title"));
        }
        if(StringUtils.isNotEmpty(param.get("orgId"))){
            entityWrapper.eq("org_id",param.get("orgId"));
        }
        List<BaseOrg> orgList=baseOrgService.getList("",param);

        List<BaseAuth> list=baseAuthMapper.selectPage(page,entityWrapper);
        if(list!=null&&list.size()>0){
            int k=list.size();
            for (int i = 0; i < k; i++) {
                BaseAuth baseAuth=list.get(i);
                if(orgList!=null&&orgList.size()>0){
                    int h=orgList.size();
                    ok:
                    for (int j = 0; j < h; j++) {
                        BaseOrg org=orgList.get(j);
                        if(baseAuth.getOrgId().equals(org.getId())){
                            baseAuth.setOrgName(org.getOrgName());
                            break ok;
                        }
                    }
                }
            }
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public void insertBean(String sixCode, BaseAuth bean) {
        baseAuthMapper.insert(bean);
    }

    @Override
    public Integer deleteById(String sixCode, String id) {
        Integer count=baseAuthMapper.deleteById(id);
        return count;
    }

    @Override
    public Integer deleteByOrgId(String sixCode, String orgId) {
        Integer count=baseAuthMapper.deleteByOrgId(orgId);
        return count;
    }

    @Override
    public Integer updateById(String sixCode, BaseAuth bean) {
        Integer count=baseAuthMapper.updateById(bean);
        return count;
    }

    @Override
    public Integer updateA8Bean(String sixCode) {
        Integer count=baseAuthMapper.updateA8Bean(sixCode);
        return count;
    }

    @Override
    public BaseAuth findById(String sixCode, String id) {
        BaseAuth bean=baseAuthMapper.selectById(id);
        return bean;
    }

    @Override
    public BaseAuth findByorgId(String sixCode, String orgId) {
        BaseAuth queryBean=new BaseAuth();
        queryBean.setOrgId(orgId);
        BaseAuth bean=baseAuthMapper.selectOne(queryBean);
        return bean;
    }


    @Override
    public Integer getCountByOrgId(String sixCode, String orgId) {
        Integer count=baseAuthMapper.getCountByOrgId(orgId);
        return count;
    }
}
