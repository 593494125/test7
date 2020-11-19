package com.springboot.service.barcode;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.barcode.BarcodeDevice;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-06-01
 */
public interface BarcodeDeviceService extends IService<BarcodeDevice> {

    Page<BarcodeDevice> getPage(String sixCode, Page<BarcodeDevice> page, BarcodeDevice barcodeDevice);

    void insertBean(String sixCode,BarcodeDevice bean);

    void deleteById(String sixCode,String id);

    Integer updateById(String sixCode, BarcodeDevice bean);

    BarcodeDevice findById(String sixCode,String id);

    BarcodeDevice findByMax(String sixCode,String max);

}
