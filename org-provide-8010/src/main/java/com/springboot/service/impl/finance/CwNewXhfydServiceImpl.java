package com.springboot.service.impl.finance;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.finance.CwNewXhfydMapper;
import com.springboot.model.finance.CwNewXhfyd;
import com.springboot.service.finance.CwNewXhfydService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 财务销货费用单 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CwNewXhfydServiceImpl extends ServiceImpl<CwNewXhfydMapper, CwNewXhfyd> implements CwNewXhfydService {

    @Resource
    private CwNewXhfydMapper cwNewXhfydMapper;
    @Override
    public CwNewXhfyd findByPzh(String sixCode, String pzh) {
        CwNewXhfyd bean=cwNewXhfydMapper.findByPzh(pzh);
        return bean;
    }
}
