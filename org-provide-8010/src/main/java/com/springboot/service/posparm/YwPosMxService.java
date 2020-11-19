package com.springboot.service.posparm;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.posparm.YwPosMx;
import com.springboot.model.posparm.YwPosSaleDay;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;

/**
 * <p>
 * POS明细表 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-07-10
 */
public interface YwPosMxService extends IService<YwPosMx> {

    YwCgRkdPrintmx getLsposDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org);

    YwPosSaleDay findYwSaleDayDetail(String sixCode, String bmbh, String jqbh, String rq, DaQxYhda daYgda, BaseOrg org);

}
