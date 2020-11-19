package com.springboot.service.impl.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.goods.DaSpCmbtMapper;
import com.springboot.model.goods.DaSpCmbt;
import com.springboot.service.goods.DaSpCmbtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DaSpCmbtServiceImpl extends ServiceImpl<DaSpCmbtMapper, DaSpCmbt> implements DaSpCmbtService {

    @Resource
    private DaSpCmbtMapper daSpCmbtMapper;


    @Override
    public DaSpCmbt findByCmzbh(String sixCode, String cmzbh) {
        DaSpCmbt bean=daSpCmbtMapper.findByCmzbh(cmzbh);
        return bean;
    }

    @Override
    public List<DaSpCmbt> getList(String sixCode) {
        List<DaSpCmbt> list=daSpCmbtMapper.selectList(new EntityWrapper<DaSpCmbt>());
        return list;
    }
}
