package com.springboot.service.impl.posparm;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.posparm.YwPosHzMapper;
import com.springboot.model.posparm.YwPosHz;
import com.springboot.service.posparm.YwPosHzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * POS汇总表 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-07-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwPosHzServiceImpl extends ServiceImpl<YwPosHzMapper, YwPosHz> implements YwPosHzService {

    @Resource
    private YwPosHzMapper ywPosHzMapper;
    @Override
    public YwPosHz findByPzh(String sixCode, String pzh) {
        YwPosHz bean =ywPosHzMapper.findByPzh(pzh);
        return bean;
    }
}
