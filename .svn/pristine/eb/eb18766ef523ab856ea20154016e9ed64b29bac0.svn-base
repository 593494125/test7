package com.springboot.service.bluetooth;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.bluetooth.BlueToothExt;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-08-26
 */
public interface BlueToothExtService extends IService<BlueToothExt> {

    Page<BlueToothExt> getPage(String sixCode, Page<BlueToothExt> page, BlueToothExt blueToothExt);

    List<BlueToothExt> getList(String sixCode, Map<String, String> param);

    BlueToothExt findByMac(String sixCode, String mac);

    BlueToothExt findById(String sixCode, String id);

    Integer insertBean(String sixCode, BlueToothExt bean);

    boolean saveAll(String sixCode, List<BlueToothExt> list);

    Integer deleteById(String sixCode,String id);

    Integer updateById(String sixCode, BlueToothExt bean);

}
