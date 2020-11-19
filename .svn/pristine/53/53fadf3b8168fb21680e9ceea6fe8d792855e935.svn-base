package com.springboot.service.impl.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.goods.DaSpCmbtMapper;
import com.springboot.dao.goods.DaSpCmdmMapper;
import com.springboot.model.goods.DaSpCmdm;
import com.springboot.service.goods.DaSpCmdmService;
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
 * @since 2020-05-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DaSpCmdmServiceImpl extends ServiceImpl<DaSpCmdmMapper, DaSpCmdm> implements DaSpCmdmService {


    @Resource
    private DaSpCmdmMapper daSpCmdmMapper;
    @Override
    public List<DaSpCmdm> getList(String sixCode) {
        List<DaSpCmdm> list=daSpCmdmMapper.selectList(new EntityWrapper<DaSpCmdm>());
        return list;
    }
}
