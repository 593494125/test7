package com.springboot.service.impl.tm;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.tm.DaSpTmfaszPpMapper;
import com.springboot.model.tm.DaSpTmfaszPp;
import com.springboot.model.tm.DaSpTmfaszPpJson;
import com.springboot.model.tm.FjTmbhJson1;
import com.springboot.model.tm.SystemTmfa1;
import com.springboot.service.tm.DaSpTmfaszPpService;
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
 * @since 2020-06-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaSpTmfaszPpServiceImpl extends ServiceImpl<DaSpTmfaszPpMapper, DaSpTmfaszPp> implements DaSpTmfaszPpService {

    @Resource
    private DaSpTmfaszPpMapper daSpTmfaszPpMapper;

    @Override
    public SystemTmfa1 getSystemTmbh(String sixCode) {
        SystemTmfa1 bean=daSpTmfaszPpMapper.getSystemTmbh();
        return bean;
    }

    @Override
    public List<DaSpTmfaszPpJson> getPpTmbhList(String sixCode) {
        List<DaSpTmfaszPpJson> list=daSpTmfaszPpMapper.getPpTmbhList();
        return list;
    }

    @Override
    public FjTmbhJson1 getFjTmbh(String sixCode,String ppTmbh) {
        FjTmbhJson1 bean=daSpTmfaszPpMapper.getFjTmbh(ppTmbh);
        return bean;
    }

    @Override
    public List<FjTmbhJson1> getAllFjTmbh(String sixCode) {
        List<FjTmbhJson1> list=daSpTmfaszPpMapper.getAllFjTmbh();
        return list;
    }

}
