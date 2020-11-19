package com.springboot.service.impl.goods;

import com.springboot.dao.goods.DaSpBxdaMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.goods.DaSpBxda;
import com.springboot.service.goods.DaSpBxdaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
 * @since 2020-04-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaSpBxdaServiceImpl extends ServiceImpl<DaSpBxdaMapper, DaSpBxda> implements DaSpBxdaService {

    @Resource
    private DaSpBxdaMapper daSpBxdaMapper;
    @Override
    public String findByBxbh(String sixCode, String bxbh) {
        String bxmc=daSpBxdaMapper.findByBxbh(bxbh);
        return bxmc;
    }

    @Override
    public List<DaSpBxda> findList(String sixCode) {
        List<DaSpBxda> list=daSpBxdaMapper.findList();
        return list;
    }

    @Override
    public DaSpBxbt findSystemQybz(String sixCode) {
        DaSpBxbt qybz= daSpBxdaMapper.findSystemQybz();
        return qybz;
    }

}
