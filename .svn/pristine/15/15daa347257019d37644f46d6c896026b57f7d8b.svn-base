package com.springboot.service.impl.bluetooth;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.bluetooth.BlueToothSettingMapper;
import com.springboot.model.bluetooth.BlueToothSetting;
import com.springboot.service.bluetooth.BlueToothSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BlueToothSettingServiceImpl extends ServiceImpl<BlueToothSettingMapper, BlueToothSetting> implements BlueToothSettingService {

    @Resource
    private BlueToothSettingMapper blueToothSettingMapper;

    @Override
    public Page<BlueToothSetting> getPage(String sixCode, Page<BlueToothSetting> page, Map<String, String> param) {
        EntityWrapper<BlueToothSetting> entityWrapper=new EntityWrapper<BlueToothSetting>();
        List<BlueToothSetting> list=blueToothSettingMapper.selectPage(page,entityWrapper);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<BlueToothSetting> getList(String sixCode, Map<String, String> param) {
        List<BlueToothSetting> list=blueToothSettingMapper.selectList(new EntityWrapper<BlueToothSetting>());
        return list;
    }

    @Override
    public BlueToothSetting findByServiceTz(String sixCode, BlueToothSetting bean) {
        BlueToothSetting newbean=blueToothSettingMapper.findByServiceTz(bean.getServiceTz());
        return newbean;
    }

    @Override
    public Integer insertBean(String sixCode, BlueToothSetting bean) {
        Integer flag=blueToothSettingMapper.insert(bean);
        return flag;
    }

    @Override
    public Integer deleteById(String sixCode, String id) {
        Integer flag=blueToothSettingMapper.deleteById(id);
        return flag;
    }

    @Override
    public Integer updateById(String sixCode, BlueToothSetting bean) {
        Integer flag=blueToothSettingMapper.updateById(bean);
        return flag;
    }
}
