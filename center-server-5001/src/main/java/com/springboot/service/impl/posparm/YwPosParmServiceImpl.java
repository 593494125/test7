package com.springboot.service.impl.posparm;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.RedisUtil;
import com.springboot.dao.department.DaBmdaMapper;
import com.springboot.dao.posparm.YwPosParmMapper;
import com.springboot.model.department.DaBmda;
import com.springboot.model.posparm.YwPosParm;
import com.springboot.service.posparm.YwPosParmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-07-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwPosParmServiceImpl extends ServiceImpl<YwPosParmMapper, YwPosParm> implements YwPosParmService {


    @Resource
    private YwPosParmMapper ywPosParmMapper;
    @Resource
    private DaBmdaMapper daBmdaMapper;
    @Resource
    private RedisUtil redisUtil;
    @Override
    public YwPosParm findByJqbh(String sixCode, String jqbh,String bmbh) {
        YwPosParm bean=null;
        if(StringUtils.isNotEmpty(jqbh)){
            YwPosParm querybean=new YwPosParm();
            querybean.setJqbh(jqbh);
            bean=ywPosParmMapper.selectOne(querybean);
            if(bean!=null){
                DaBmda queryBmdaBean=new DaBmda();
                queryBmdaBean.setBmbh(bean.getBmbh());
                DaBmda daBmda=daBmdaMapper.selectOne(queryBmdaBean);
                if(daBmda!=null){
                    bean.setBmmc(daBmda.getBmmc());
                }
                //是否允许负库存
                String isAllowStock=ywPosParmMapper.isAllowStock(bmbh);
                if(StringUtils.isNotEmpty(isAllowStock)){
                    bean.setIsAllowStock(isAllowStock);
                    redisUtil.set(sixCode,sixCode+"_"+bmbh+"_isAllowStock",isAllowStock);
                }
            }
        }
        return bean;
    }

    @Override
    public String findXsmrjg(String sixCode, String jqbh) {
        String xsmrjg=ywPosParmMapper.findXsmrjg(jqbh);
        return xsmrjg;
    }
    @Override
    public String findBmbhByQtjqm(String sixCode, String jqbh) {
        String bmbh=ywPosParmMapper.findBmbhByQtjqm(jqbh);
        return bmbh;
    }

    @Override
    public String isAllowStock(String sixCode, String bmbh) {
        String isAllowStock=ywPosParmMapper.isAllowStock(bmbh);
        return isAllowStock;
    }
}
