package com.springboot.dao.bluetooth;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.bluetooth.BlueToothExt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-08-26
 */
public interface BlueToothExtMapper extends BaseMapper<BlueToothExt> {

    List<BlueToothExt> getPage(@Param("key") String key,@Param("deviceName") String deviceName ,@Param("type") String type ,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    BlueToothExt findByMac(@Param("mac") String mac);

    boolean insertBatch(List<BlueToothExt> list);
}
