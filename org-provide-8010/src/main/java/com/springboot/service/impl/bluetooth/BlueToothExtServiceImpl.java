package com.springboot.service.impl.bluetooth;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.JBDate;
import com.springboot.dao.bluetooth.BlueToothExtMapper;
import com.springboot.model.bluetooth.BlueToothExt;
import com.springboot.service.bluetooth.BlueToothExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-08-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BlueToothExtServiceImpl extends ServiceImpl<BlueToothExtMapper, BlueToothExt> implements BlueToothExtService {

    @Resource
    private BlueToothExtMapper blueToothExtMapper;

    @Override
    public Page<BlueToothExt> getPage(String sixCode, Page<BlueToothExt> page, BlueToothExt blueToothExt) {
        EntityWrapper<BlueToothExt> entityWrapper=new EntityWrapper<BlueToothExt>();
        Integer count =0;
        Integer pageNo=blueToothExt.getPageNo();
        Integer pageSize=blueToothExt.getPageSize();
        String type=blueToothExt.getType();
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(blueToothExt.getKey())){
            param.put("key",blueToothExt.getKey());
        }
        if(StringUtils.isNotEmpty(type)&&"1".equals(type)){
            blueToothExt.setDeviceName("iData");
            count =blueToothExtMapper.selectCount(entityWrapper.eq("device_name",blueToothExt.getDeviceName()));
        }else{
            blueToothExt.setDeviceName("iData");
            blueToothExt.setType("0");
            count =blueToothExtMapper.selectCount(entityWrapper.ne("device_name",blueToothExt.getDeviceName()));
        }
        List<BlueToothExt> list=blueToothExtMapper.getPage(param.get("key"),blueToothExt.getDeviceName(),blueToothExt.getType(),pageNo,pageSize);
        page.setRecords(list);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        page.setTotal(count);
        return page;
    }

    @Override
    public List<BlueToothExt> getList(String sixCode, Map<String, String> param) {
        List<BlueToothExt> list=blueToothExtMapper.selectList(new EntityWrapper<BlueToothExt>());
        return list;
    }

    @Override
    public BlueToothExt findByMac(String sixCode, String mac) {
        BlueToothExt blueToothExt=blueToothExtMapper.findByMac(mac);
        return blueToothExt;
    }

    @Override
    public BlueToothExt findById(String sixCode, String id) {
        BlueToothExt bean=blueToothExtMapper.selectById(id);
        return bean;
    }

    @Override
    public Integer insertBean(String sixCode, BlueToothExt bean) {
        Integer flag=blueToothExtMapper.insert(bean);
        return flag;
    }

    @Override
    public boolean saveAll(String sixCode, List<BlueToothExt> list) {
        boolean flag=blueToothExtMapper.insertBatch(list);
        return flag;
    }

    @Override
    public Integer deleteById(String sixCode, String id) {
        Integer flag=blueToothExtMapper.deleteById(id);
        return flag;
    }

    @Override
    public Integer updateById(String sixCode, BlueToothExt bean) {
        Integer flag=blueToothExtMapper.updateById(bean);
        return flag;
    }
}
