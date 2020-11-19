package com.springboot.service.impl.customer;

import com.springboot.dao.customer.DaKhdaMapper;
import com.springboot.dao.department.DaBmdaMapper;
import com.springboot.model.customer.DaKhda;
import com.springboot.model.customer.DaKhdaJson;
import com.springboot.model.department.DaBmdaJson;
import com.springboot.service.customer.DaKhdaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 客户档案 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DaKhdaServiceImpl extends ServiceImpl<DaKhdaMapper, DaKhda> implements DaKhdaService {

    @Resource
    private DaKhdaMapper daKhdaMapper;

    @Override
    public List<DaKhdaJson> findKhda(String sixCode, String yhbh) {
        List<DaKhdaJson> list=daKhdaMapper.findKhda(yhbh);
        return list;
    }
}
