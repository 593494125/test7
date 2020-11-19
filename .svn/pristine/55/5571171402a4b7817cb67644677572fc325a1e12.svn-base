package com.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springboot.dao.user.a8.BaseUserMapper;
import com.springboot.model.user.BaseUser;
import com.springboot.service.user.BaseUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2019/10/18.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@DefaultProperties(defaultFallback = "globalTimeOutService",commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
})
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements BaseUserService {
    @Resource
    private BaseUserMapper baseUserMapper;

    @Override
    public Page<BaseUser> getPage(String orgId,Page<BaseUser> page, Map<String, String> param) {
        List<BaseUser> list=baseUserMapper.selectPage(page,new EntityWrapper<BaseUser>());
        page.setRecords(list);
        return page;
    }

    @Override
    public void insertBean(String orgId,BaseUser bean) {
        baseUserMapper.insert(bean);
    }

    @Override
    public Integer updateById(String orgId, BaseUser bean) {
        Integer count=baseUserMapper.updateById(bean);
        return count;
    }

    @Override
    public Integer deleteById(String orgId, String id) {
        Integer count=baseUserMapper.deleteById(id);
        return count;
    }

    @Override
    public BaseUser findByName(String orgId, String userName) {
        List<BaseUser> list=baseUserMapper.selectList(new EntityWrapper<BaseUser>().eq("userName", userName));
        BaseUser bean=null;
        if(list!=null&&list.size()>0){
            bean=list.get(0);
        }
        return bean;
    }

    @Override
    public BaseUser findByNameAndPassWord(String orgId, String userName, String passWord) {
        BaseUser queryBean=new BaseUser();
        queryBean.setUserName(userName);
        queryBean.setPassWord(passWord);
        BaseUser bean=baseUserMapper.selectOne(queryBean);
        return bean;
    }

    @Override
    @HystrixCommand
    public String findByUserName(String id,String userName) {
        Integer count=0;
//        int k=7/0;
//        try {
//            TimeUnit.MILLISECONDS.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        count=baseUserMapper.findByUserName(id,userName);
        if(count==null){
            count=0;
        }
        return count.toString();
    }

    public String globalTimeOutService(){
        String result="服务端繁忙，请稍后重试";
        return result;
    }

}
