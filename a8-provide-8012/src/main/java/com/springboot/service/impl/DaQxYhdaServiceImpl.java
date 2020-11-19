package com.springboot.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.department.DaBmdaMapper;
import com.springboot.dao.user.a8.DaQxYhdaMapper;
import com.springboot.model.department.DaBmda;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.user.DaQxYhdaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户档案 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-06-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaQxYhdaServiceImpl extends ServiceImpl<DaQxYhdaMapper, DaQxYhda> implements DaQxYhdaService {

    @Resource
    private DaQxYhdaMapper daQxYhdaMapper;
    @Resource
    private DaBmdaMapper daBmdaMapper;
    @Override
    public DaQxYhda findByNameAndPassWord(String sixCode, String userName, String passWord) {
//        DaQxYhda bean=daQxYhdaMapper.findByNameAndPassWord(userName,passWord);
        DaQxYhda queryBean=new DaQxYhda();
        queryBean.setYhbh(userName);
        queryBean.setYhmm(passWord);
        DaQxYhda bean=daQxYhdaMapper.selectOne(queryBean);
        if(bean!=null){
            DaBmda queryBmdaBean=new DaBmda();
            queryBmdaBean.setBmbh(bean.getBmbh());
            DaBmda daBmda=daBmdaMapper.selectOne(queryBmdaBean);
            if(daBmda!=null){
                bean.setBmmc(daBmda.getBmmc());
            }
        }
        return bean;
    }

    @Override
    public DaQxYhda findByName(String sixCode, String userName) {
        DaQxYhda queryBean=new DaQxYhda();
        queryBean.setYhbh(userName);
        DaQxYhda bean=daQxYhdaMapper.findA8One(userName);
//        if(bean!=null){
//            DaBmda queryBmdaBean=new DaBmda();
//            queryBmdaBean.setBmbh(bean.getBmbh());
//            DaBmda daBmda=daBmdaMapper.selectOne(queryBmdaBean);
//            if(daBmda!=null){
//                bean.setBmmc(daBmda.getBmmc());
//            }
//        }
        return bean;
    }
}
