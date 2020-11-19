package com.springboot.service.impl.datasource;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springboot.common.RedisUtil;
import com.springboot.common.SystemConstant;
import com.springboot.dao.datasource.BaseDatasourceMapper;
import com.springboot.model.datasource.BaseDatasource;
import com.springboot.model.org.BaseOrg;
import com.springboot.service.datasource.BaseDatasourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2019/10/18.
 */
@Service
@Transactional(rollbackFor = Exception.class)
//@DefaultProperties(defaultFallback = "globalTimeOutService",commandProperties = {
//        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//})
public class BaseDatasourceServiceImpl extends ServiceImpl<BaseDatasourceMapper, BaseDatasource> implements BaseDatasourceService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseDatasourceMapper baseDatasourceMapper;

    @Override
    @HystrixCommand
    public Page<BaseDatasource> getPage(String orgId,Page<BaseDatasource> page, Map<String, String> param) {
        EntityWrapper<BaseDatasource> entityWrapper=new EntityWrapper<BaseDatasource>();
        if(StringUtils.isNotEmpty(param.get("orgId"))){
            entityWrapper.eq("org_id",param.get("orgId"));
        }
        List<BaseDatasource> list=baseDatasourceMapper.selectPage(page,entityWrapper);
        page.setRecords(list);
        return page;
    }

    @Override
    public void insertBean(String orgId, BaseDatasource bean) {
        baseDatasourceMapper.insert(bean);
    }
    @Override
    public void deleteById(String orgId,String id) {
        Map<String,String> param=new HashMap<String,String>();
        baseDatasourceMapper.deleteById(id);
        List<BaseDatasource> list=baseDatasourceMapper.getList(param);
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"dataSourcesList", JSON.toJSON(list).toString());
    }

    @Override
    public void deleteByOrgId(String sixCode, String orgId) {
        Map<String,String> param=new HashMap<String,String>();
        baseDatasourceMapper.deleteByOrgId(orgId);
        List<BaseDatasource> list=baseDatasourceMapper.getList(param);
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"dataSourcesList", JSON.toJSON(list).toString());
    }

    @Override
    public Integer updateById(String orgId, BaseDatasource baseDatasource) {
        Integer count=baseDatasourceMapper.updateById(baseDatasource);
        return count;
    }

    @Override
    public Integer getCountByOrgId(String sixCode, String orgId) {
        Integer count=baseDatasourceMapper.getCountByOrgId(orgId);
        return count;
    }

    @Override
    public List<BaseDatasource> getList(String orgId,Map<String, String> param) {
        List<BaseDatasource> list=baseDatasourceMapper.getList(param);
        return list;
    }
    @Override
    public BaseDatasource getBeanByOrgId(String sixCode, String orgId) {
        BaseDatasource bean=baseDatasourceMapper.getBeanByOrgId(orgId);
        return bean;
    }
//    @Override
//    public BaseDatasource findByOrgId(String orgId1,String orgId) {
//        List<BaseDatasource> list=baseDatasourceMapper.selectList(new EntityWrapper<BaseDatasource>().eq("org_id",orgId));
//        BaseDatasource bean=null;
//        if(list!=null&&list.size()>0){
//            bean=list.get(0);
//        }
//        return bean;
//    }

//    @Override
//    @HystrixCommand
//    public String findByUserName(String id,String userName) {
//        Integer count=0;
////        int k=7/0;
////        try {
////            TimeUnit.MILLISECONDS.sleep(3000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        count=baseUserMapper.findByUserName(id,userName);
//        if(count==null){
//            count=0;
//        }
//        return count.toString();
//    }

    public String globalTimeOutService(){
        String result="服务端繁忙，请稍后重试";
        return result;
    }

}
