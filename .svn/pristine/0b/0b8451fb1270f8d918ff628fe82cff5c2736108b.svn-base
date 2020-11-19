package com.springboot.service.impl.posparm;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.posparm.YwPosParmMapper;
import com.springboot.model.posparm.YwPosParm;
import com.springboot.service.posparm.YwPosParmService;
import org.springframework.stereotype.Service;

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
public class YwPosParmServiceImpl extends ServiceImpl<YwPosParmMapper, YwPosParm> implements YwPosParmService {


    @Resource
    private YwPosParmMapper ywPosParmMapper;
    @Override
    public YwPosParm findByJqbh(String sixCode, String jqbh) {
        YwPosParm querybean=new YwPosParm();
        querybean.setJqbh(jqbh);
        YwPosParm bean=ywPosParmMapper.selectOne(querybean);
        return bean;
    }
}
