package com.springboot.service.bluetooth;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.bluetooth.BlueToothSetting;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-07-08
 */
public interface BlueToothSettingService extends IService<BlueToothSetting> {

    Page<BlueToothSetting> getPage(String sixCode, Page<BlueToothSetting> page, Map<String, String> param);

    List<BlueToothSetting> getList(String sixCode, Map<String, String> param);

    BlueToothSetting findByServiceTz(String sixCode, BlueToothSetting bean);

    Integer insertBean(String sixCode, BlueToothSetting bean);

    Integer deleteById(String sixCode,String id);

    Integer updateById(String sixCode, BlueToothSetting bean);

}
