package com.springboot.service.impl.stock;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.stock.YwKcDjztPcMapper;
import com.springboot.model.stock.YwKcDjztPc;
import com.springboot.service.stock.YwKcDjztPcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwKcDjztPcServiceImpl extends ServiceImpl<YwKcDjztPcMapper, YwKcDjztPc> implements YwKcDjztPcService {

    @Resource
    private YwKcDjztPcMapper ywKcDjztPcMapper;


    @Override
    public Integer batchDeleteByPzh(String sixCode, String pzh) {
        Integer flag=ywKcDjztPcMapper.batchDeleteByPzh(pzh);
        return flag;
    }
}
