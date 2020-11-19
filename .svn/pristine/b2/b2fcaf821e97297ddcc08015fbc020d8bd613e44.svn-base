package com.springboot.service.impl.stock;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.stock.YwKcDjztMapper;
import com.springboot.model.stock.YwKcDjzt;
import com.springboot.service.stock.YwKcDjztService;
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
 * @since 2020-05-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwKcDjztServiceImpl extends ServiceImpl<YwKcDjztMapper, YwKcDjzt> implements YwKcDjztService {

    @Resource
    private YwKcDjztMapper ywKcDjztMapper;


    @Override
    public Integer batchDeleteByPzh(String sixCode, String pzh) {
        Integer flag=ywKcDjztMapper.batchDeleteByPzh(pzh);
        return flag;
    }
}
