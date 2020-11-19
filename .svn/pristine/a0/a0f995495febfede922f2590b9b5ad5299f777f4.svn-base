package com.springboot.service.impl.goods;

import com.springboot.dao.goods.DaSpYsdaMapper;
import com.springboot.model.goods.DaSpYsda;
import com.springboot.service.goods.DaSpYsdaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品颜色档案 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaSpYsdaServiceImpl extends ServiceImpl<DaSpYsdaMapper, DaSpYsda> implements DaSpYsdaService {

    @Resource
    private DaSpYsdaMapper daSpYsdaMapper;
    @Override
    public String findByYsbh(String sixCode, String yslsh,String ysbh) {
        String ysmc=daSpYsdaMapper.findByYsbh(yslsh,ysbh);
        return ysmc;
    }

    @Override
    public List<DaSpYsda> findList(String sixCode) {
        List<DaSpYsda> list=daSpYsdaMapper.findList();
        return list;
    }
}
