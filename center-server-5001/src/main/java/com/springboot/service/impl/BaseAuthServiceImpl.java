package com.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.JBDate;
import com.springboot.common.RedisUtil;
import com.springboot.common.SixCode;
import com.springboot.common.SystemConstant;
import com.springboot.core.ResultGenerator;
import com.springboot.dao.user.BaseAuthMapper;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.user.BaseAuth;
import com.springboot.service.org.BaseOrgService;
import com.springboot.service.user.BaseAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
@Slf4j
public class BaseAuthServiceImpl extends ServiceImpl<BaseAuthMapper, BaseAuth> implements BaseAuthService {


    @Resource
    private BaseAuthMapper baseAuthMapper;
    @Resource
    private BaseOrgService baseOrgService;
    @Resource
    private RedisUtil redisUtil;
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
                            baseAuth.setCount(org.getCount().toString());
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
    public BaseAuth findBySixCode(String sixCode1,String sixCode) {
        BaseAuth queryBean=new BaseAuth();
        queryBean.setSixCode(sixCode);
        BaseAuth bean=baseAuthMapper.selectOne(queryBean);
        return bean;
    }

    @Override
    public Integer getCountByOrgId(String sixCode, String orgId) {
        Integer count=baseAuthMapper.getCountByOrgId(orgId);
        return count;
    }

//    @Override
//    public BaseAuth insertBean(String sixCode, BaseAuth baseAuth) {
//        BaseAuth bean=null;
//        baseAuth.setState(1);//0:过期1：正常
//        //生成六位码
//        sixCode= SixCode.getStringRandom(5);
//        //将sixCode放入redis中,登录的时候通过六位码获取组织id判断是哪家企业
//        String oldSixCode=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,baseAuth.getOrgId());
//        if(StringUtils.isEmpty(oldSixCode)){
//            BaseAuth queryBean=new BaseAuth();
//            queryBean.setOrgId(baseAuth.getOrgId());
//            bean=baseAuthMapper.selectOne(queryBean);
//            if(bean!=null){
//                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId(),bean.getSixCode());
//                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+bean.getSixCode(),bean.getOrgId());//通过六位码获取组织id
//                baseAuth.setSixCode(bean.getSixCode());
//            }else{
//                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseAuth.getOrgId(),sixCode);
//                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode,baseAuth.getOrgId());//通过六位码获取组织id
//                baseAuth.setSixCode(sixCode);
//            }
//
//        }else{
//            if(bean!=null){
//                //直接返回数据库查的信息
//            }else{
//                //数据库删除了，缓存中存在
//                baseAuth.setSixCode(oldSixCode);
//            }
//        }
//        if(bean!=null){
//            //向a8数据库插入五位码
//            baseAuthMapper.updateA8Bean(bean.getSixCode());
//            return bean;
//        }else{
//            baseAuth.setEffectStartTime(JBDate.getNowDate(new Date().getTime()));
//            baseAuthMapper.insert(baseAuth);
//            //向a8数据库插入五位码
//            baseAuthMapper.updateA8Bean(baseAuth.getSixCode());//必须要有数据源，如果没有数据源不知道往哪一家客户表里面添加数据
//            return baseAuth;
//        }
//    }
}
