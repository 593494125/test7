package com.springboot.service.impl.barcode;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.SystemConstant;
import com.springboot.dao.barcode.BarcodeDeviceMapper;
import com.springboot.dao.purchase.YwCgRkdhzMapper;
import com.springboot.model.barcode.BarcodeDevice;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdhz;
import com.springboot.service.barcode.BarcodeDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-06-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BarcodeDeviceServiceImpl extends ServiceImpl<BarcodeDeviceMapper, BarcodeDevice> implements BarcodeDeviceService {

    @Resource
    private BarcodeDeviceMapper barcodeDeviceMapper;
    @Override
    public Page<BarcodeDevice> getPage(String sixCode, Page<BarcodeDevice> page, BarcodeDevice barcodeDevice) {
        List<BarcodeDevice> list=barcodeDeviceMapper.selectPage(page,new EntityWrapper<BarcodeDevice>().orderDesc(Arrays.asList(new String[]{"create_time"})));
        page.setRecords(list);
        return page;
    }

    @Override
    public void insertBean(String sixCode, BarcodeDevice bean) {
        barcodeDeviceMapper.insert(bean);
    }

    @Override
    public void deleteById(String sixCode, String id) {
        barcodeDeviceMapper.deleteById(id);
    }

    @Override
    public BarcodeDevice findById(String sixCode, String id) {
        BarcodeDevice bean=barcodeDeviceMapper.selectById(id);
        return bean;
    }
    @Override
    public BarcodeDevice findByMax(String sixCode, String max) {
        BarcodeDevice bean=barcodeDeviceMapper.selectById(max);
        return bean;
    }

    @Override
    public Integer updateById(String sixCode, BarcodeDevice bean) {
        Integer count=barcodeDeviceMapper.updateById(bean);
        return count;
    }
}
