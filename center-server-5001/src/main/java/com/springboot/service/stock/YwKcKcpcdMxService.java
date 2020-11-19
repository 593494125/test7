package com.springboot.service.stock;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.stock.YwKcKcpcdMx;
import com.springboot.model.user.DaQxYhda;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-18
 */
public interface YwKcKcpcdMxService extends IService<YwKcKcpcdMx> {


    Map<String,Object> getPage(String sixCode,Map<String,String> param);

    Integer batchDeleteByPzh(String sixCode,String pzh);

    YwCgRkdPrintmx getKcDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org,String ywType);
}
