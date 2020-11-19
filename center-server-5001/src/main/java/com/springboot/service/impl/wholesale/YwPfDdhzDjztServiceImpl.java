package com.springboot.service.impl.wholesale;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.wholesale.YwPfDdhzDjztMapper;
import com.springboot.model.wholesale.YwPfDdhzDjzt;
import com.springboot.service.wholesale.YwPfDdhzDjztService;
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
 * @since 2020-05-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwPfDdhzDjztServiceImpl extends ServiceImpl<YwPfDdhzDjztMapper, YwPfDdhzDjzt> implements YwPfDdhzDjztService {

    @Resource
    private YwPfDdhzDjztMapper ywPfDdhzDjztMapper;


    @Override
    public Integer batchDeleteByPzh(String sixCode, String pzh) {
        Integer flag=ywPfDdhzDjztMapper.batchDeleteByPzh(pzh);
        return flag;
    }
}
