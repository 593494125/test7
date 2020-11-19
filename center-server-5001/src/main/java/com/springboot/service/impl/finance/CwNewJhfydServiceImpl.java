package com.springboot.service.impl.finance;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.finance.CwNewJhfydMapper;
import com.springboot.model.finance.CwNewJhfyd;
import com.springboot.service.finance.CwNewJhfydService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 财务进货费用单 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CwNewJhfydServiceImpl extends ServiceImpl<CwNewJhfydMapper, CwNewJhfyd> implements CwNewJhfydService {


    @Resource
    private CwNewJhfydMapper cwNewJhfydMapper;
    @Override
    public CwNewJhfyd findByPzh(String sixCode, String pzh) {
        CwNewJhfyd bean=cwNewJhfydMapper.findByPzh(pzh);
        return bean;
    }
}
