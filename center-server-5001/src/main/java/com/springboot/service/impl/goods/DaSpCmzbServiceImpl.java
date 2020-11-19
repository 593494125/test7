package com.springboot.service.impl.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.goods.DaSpCmdmMapper;
import com.springboot.dao.goods.DaSpCmzbMapper;
import com.springboot.model.goods.DaSpCmzb;
import com.springboot.service.goods.DaSpCmzbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品尺码组表 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DaSpCmzbServiceImpl extends ServiceImpl<DaSpCmzbMapper, DaSpCmzb> implements DaSpCmzbService {

    @Resource
    private DaSpCmzbMapper daSpCmzbMapper;
    @Override
    public List<DaSpCmzb> getList(String sixCode) {
        List<DaSpCmzb> list=daSpCmzbMapper.selectList(new EntityWrapper<DaSpCmzb>());
        return list;
    }
}
