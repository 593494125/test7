package com.springboot.service.impl.transfer;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.transfer.YwDbDdhzDjztMapper;
import com.springboot.model.transfer.YwDbDdhzDjzt;
import com.springboot.service.transfer.YwDbDdhzDjztService;
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
 * @since 2020-05-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwDbDdhzDjztServiceImpl extends ServiceImpl<YwDbDdhzDjztMapper, YwDbDdhzDjzt> implements YwDbDdhzDjztService {

    @Resource
    private YwDbDdhzDjztMapper ywDbDdhzDjztMapper;


    @Override
    public Integer batchDeleteByPzh(String sixCode, String pzh) {
        Integer flag=ywDbDdhzDjztMapper.batchDeleteByPzh(pzh);
        return flag;
    }
}
