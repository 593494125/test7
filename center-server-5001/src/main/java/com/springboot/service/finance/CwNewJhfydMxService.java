package com.springboot.service.finance;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.finance.CwNewJhfydMx;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;

/**
 * <p>
 * 进货费用单明细 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewJhfydMxService extends IService<CwNewJhfydMx> {

    YwCgRkdPrintmx getCwJhfydDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org, String ywType);
}
