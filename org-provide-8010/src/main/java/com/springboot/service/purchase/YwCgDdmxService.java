package com.springboot.service.purchase;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.purchase.YwCgDdmx;

import java.util.List;

/**
 * <p>
 * (采购退货申请明细单)采购明细单 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
public interface YwCgDdmxService extends IService<YwCgDdmx> {

    String initCgDdmx(String sixCode,String flag,String tgflag);

    boolean saveBatch(String sixCode,List<YwCgDdmx> ywCgDdmxList);

}
