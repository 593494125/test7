package com.springboot.service.impl.purchase;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.purchase.YwCgDdhzDjztMapper;
import com.springboot.model.purchase.YwCgDdhzDjzt;
import com.springboot.service.purchase.YwCgDdhzDjztService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 单据状态表 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class YwCgDdhzDjztServiceImpl extends ServiceImpl<YwCgDdhzDjztMapper, YwCgDdhzDjzt> implements YwCgDdhzDjztService {

    @Resource
    private YwCgDdhzDjztMapper ywCgDdhzDjztMapper;
    @Override
    public Integer save(String sixCode,YwCgDdhzDjzt ywCgDdhzDjzt) {
        Integer count=ywCgDdhzDjztMapper.insert(ywCgDdhzDjzt);
        return count;
    }

    @Override
    public Integer batchDeleteByPzh(String sixCode, String pzh) {
        Integer flag=ywCgDdhzDjztMapper.batchDeleteByPzh(pzh);
        return flag;
    }
    //该接口供测试使用
    @Override
    public Integer batchDeleteByPzh1(String sixCode,String pzh) {

        Integer flag=ywCgDdhzDjztMapper.batchDeleteByPzh(pzh);
        return flag;
    }
}
