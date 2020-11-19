package com.springboot.service.impl.redisdatasource;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.RedisUtil;
import com.springboot.common.SystemConstant;
import com.springboot.dao.redisdatasource.BaseRedisDatasourceMapper;
import com.springboot.model.redisdatasource.BaseOrgRedisDatasource;
import com.springboot.model.redisdatasource.BaseRedisDatasource;
import com.springboot.service.redisdatasource.BaseOrgRedisDatasourceService;
import com.springboot.service.redisdatasource.BaseRedisDatasourceService;
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
 * @since 2020-07-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseRedisDatasourceServiceImpl extends ServiceImpl<BaseRedisDatasourceMapper, BaseRedisDatasource> implements BaseRedisDatasourceService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseRedisDatasourceMapper baseRedisDatasourceMapper;
    @Resource
    private BaseOrgRedisDatasourceService baseOrgRedisDatasourceService;
    @Override
    public BaseRedisDatasource findOne(String sixCode, Map<String, String> param) {
        EntityWrapper<BaseOrgRedisDatasource> entityWrapper=new EntityWrapper<BaseOrgRedisDatasource>();
        BaseRedisDatasource bean=null;
        if(StringUtils.isNotEmpty(param.get("orgId"))){
            entityWrapper.eq("org_id",param.get("orgId"));
            BaseOrgRedisDatasource baseOrgRedisDatasource=baseOrgRedisDatasourceService.selectOne(entityWrapper);
            if(baseOrgRedisDatasource!=null){
                String redisId=baseOrgRedisDatasource.getRedisId();
                if(StringUtils.isNotEmpty(redisId)){
                    bean=baseRedisDatasourceMapper.selectById(redisId);
                    bean.setOrgId(param.get("orgId"));
                }
            }
        }
        return bean;
    }

    @Override
    public List<String> getRedisSelectList(String sixCode, String areaAddress) {
        List<String> list=baseRedisDatasourceMapper.getRedisSelectList(areaAddress);
        return list;
    }

    @Override
    public List<String> getAreaSelectList(String sixCode) {
        List<String> list=baseRedisDatasourceMapper.getAreaSelectList();
        return list;
    }


    @Override
    public List<BaseRedisDatasource> getList(String sixCode, Map<String, String> param) {

        List<BaseRedisDatasource> list=baseRedisDatasourceMapper.getList(param);
        return list;
    }

    @Override
    public void insertBean(String sixCode, BaseRedisDatasource bean) {
        baseRedisDatasourceMapper.insert(bean);
    }

    @Override
    public void deleteById(String sixCode, BaseRedisDatasource bean) {
        baseRedisDatasourceMapper.deleteById(bean.getId());//删除主库
        //删除关联库
        EntityWrapper<BaseOrgRedisDatasource> entityWrapper=new EntityWrapper<BaseOrgRedisDatasource>();
        entityWrapper.eq("redis_id",bean.getId());
        List<BaseOrgRedisDatasource> list=baseOrgRedisDatasourceService.selectList(entityWrapper);
        if(list!=null&&list.size()>0){
            int k=list.size();
            for (int i = 0; i < k; i++) {
                baseOrgRedisDatasourceService.deleteById(list.get(i).getId());
            }
        }
        redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,"redisId_"+bean.getOrgId());
//        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"redisDataSourcesList", JSON.toJSON(list).toString());
    }

    @Override
    public void deleteByOrgId(String sixCode, String orgId) {
        //查看关联表
        BaseOrgRedisDatasource baseOrgRedisDatasource=baseOrgRedisDatasourceService.findByOrgId("",orgId);
        if(baseOrgRedisDatasource!=null){
            baseOrgRedisDatasourceService.deleteById(baseOrgRedisDatasource.getId());
        }
        String redisId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"redisId_"+orgId);
        if(StringUtils.isNotEmpty(redisId)){
            Integer count =baseOrgRedisDatasourceService.getCountByRedisId("",redisId);
            if(count!=null&&count>0){
            }else{
                baseRedisDatasourceMapper.deleteById(redisId);
            }
        }
    }

    @Override
    public Integer updateById(String sixCode, BaseRedisDatasource bean) {
        Integer count=baseRedisDatasourceMapper.updateById(bean);
        return count;
    }

    @Override
    public BaseRedisDatasource findById(String sixCode, String id) {
        BaseRedisDatasource bean=baseRedisDatasourceMapper.selectById(id);
        return bean;
    }

}
