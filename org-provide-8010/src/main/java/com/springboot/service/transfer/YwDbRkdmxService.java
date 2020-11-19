package com.springboot.service.transfer;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.transfer.YwDbRkdmx;
import com.springboot.model.user.DaQxYhda;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
public interface YwDbRkdmxService extends IService<YwDbRkdmx> {

    Map<String,Object> getPage(String sixCode, Map<String,String> param);

    Integer batchDeleteByPzh(String sixCode,String pzh);

    YwCgRkdPrintmx getDcDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org);
}
