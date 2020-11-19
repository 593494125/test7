package com.springboot.service.impl.department;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.department.DaBmdaMapper;
import com.springboot.model.department.DaBmda;
import com.springboot.model.department.DaBmdaJson;
import com.springboot.service.department.DaBmdaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 部门档案 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaBmdaServiceImpl extends ServiceImpl<DaBmdaMapper, DaBmda> implements DaBmdaService {

    @Resource
    private DaBmdaMapper daBmdaMapper;
    @Override
    public List<DaBmdaJson> findCgBmda(String sixCode,String yhbh) {
        List<DaBmdaJson> list=daBmdaMapper.findCgBmda(yhbh);
        return list;
    }
    @Override
    public List<DaBmdaJson> findDbcBmda(String sixCode,String yhbh) {
        List<DaBmdaJson> list=daBmdaMapper.findDbcBmda(yhbh);
        return list;
    }
    @Override
    public List<DaBmdaJson> findDbrBmda(String sixCode,String yhbh) {
        List<DaBmdaJson> list=daBmdaMapper.findDbrBmda(yhbh);
        return list;
    }
    @Override
    public List<DaBmdaJson> findPfBmda(String sixCode,String yhbh) {
        List<DaBmdaJson> list=daBmdaMapper.findPfBmda(yhbh);
        return list;
    }
    @Override
    public List<DaBmdaJson> findKcBmda(String sixCode,String yhbh) {
        List<DaBmdaJson> list=daBmdaMapper.findKcBmda(yhbh);
        return list;
    }

    @Override
    public List<DaBmdaJson> findLsPosBmda(String sixCode, String yhbh) {
        List<DaBmdaJson> list=daBmdaMapper.findLsPosBmda(yhbh);
        return list;
    }

    @Override
    public List<DaBmda> getList(String sixCode) {
        List<DaBmda> list=daBmdaMapper.getList();
        return list;
    }
}
