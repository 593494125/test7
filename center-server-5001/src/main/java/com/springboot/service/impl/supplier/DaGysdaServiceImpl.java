package com.springboot.service.impl.supplier;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.supplier.DaGysdaMapper;
import com.springboot.model.supplier.DaGysda;
import com.springboot.model.supplier.DaGysdaJson;
import com.springboot.service.supplier.DaGysdaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 供应商档案 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaGysdaServiceImpl extends ServiceImpl<DaGysdaMapper, DaGysda> implements DaGysdaService {

    @Resource
    private DaGysdaMapper daGysdaMapper;
    @Override
    public List<DaGysdaJson> findByDaGysda(String sixCode,String yhbh) {
        List<DaGysdaJson> list=daGysdaMapper.findByDaGysda(yhbh);
        return list;
    }

}
