package com.springboot.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.system.DaQjCsb;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-07-06
 */
public interface DaQjCsbService extends IService<DaQjCsb> {

    Object findBean(String sixCode);

    List<DaQjCsb> findAll(String sixCode);

    String findIsStartTmFangan(String sixCode);

    String findSystemYsxqybz(String sixCode);

    List<DaQjCsb> findByCsfl(String sixCode,String csfl);

    //批量添加
    boolean saveBatch(String sixCode, List<DaQjCsb> list);

    Integer findMaxLsh(String sixCode);

    DaQjCsb getBean(String sixCode,String csbh,String csfl);

    Integer updateByBean(String sixCode,DaQjCsb bean);
}
